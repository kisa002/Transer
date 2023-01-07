package presentation

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.isAltPressed
import androidx.compose.ui.input.key.key
import com.haeyum.common.domain.usecase.TranslateUseCase
import com.haeyum.common.domain.usecase.recent.AddRecentTranslateUseCase
import com.haeyum.common.domain.usecase.recent.GetRecentTranslatesUseCase
import com.haeyum.common.domain.usecase.saved.AddSavedTranslateUseCase
import com.haeyum.common.domain.usecase.saved.GetSavedTranslatesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalComposeUiApi::class)
class DesktopViewModel(
    private val coroutineScope: CoroutineScope,
    private val translateUseCase: TranslateUseCase,
    private val getRecentTranslatesUseCase: GetRecentTranslatesUseCase,
    private val addRecentTranslateUseCase: AddRecentTranslateUseCase,
    private val getSavedTranslatesUseCase: GetSavedTranslatesUseCase,
    private val addSavedTranslateUseCase: AddSavedTranslateUseCase
) {
    private val _isRequesting = MutableStateFlow(false)
    val isRequesting: StateFlow<Boolean> = _isRequesting

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    val commandInference = query.map { query ->
        Command.values().firstOrNull { command ->
            query.lowercase().contains(command.query.take(query.length).lowercase())
        }
    }.stateIn(scope = coroutineScope, started = SharingStarted.Lazily, initialValue = null)

    val screenState = combine(isRequesting, query, commandInference) { isRequesting, query, commandInference ->
        when {
            query.isEmpty() || (query.length == 1 && query.first() == '>') -> DesktopScreenState.Home
            commandInference != null -> commandInference.state
            query.isNotEmpty() -> DesktopScreenState.Translate
            else -> DesktopScreenState.Home
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = DesktopScreenState.Home
    )

    private val _screenEvent = MutableSharedFlow<DesktopScreenEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val screenEvent = _screenEvent.asSharedFlow()

    private val _currentSelectedIndex = MutableStateFlow(0)
    val currentSelectedIndex = _currentSelectedIndex.asStateFlow()

    val translatedText = query
        .transformLatest { query ->
            emit(
                if (query.isEmpty() || query.contains(">")) {
                    _isRequesting.value = false
                    ""
                } else {
                    _isRequesting.value = true
                    delay(700)
                    translateUseCase(q = query, key = "").translatedText
                }
            )
        }
        .onEach {
            _isRequesting.value = false
        }
        .catch {
            _isRequesting.value = false
            it.printStackTrace()
        }
        .stateIn(coroutineScope, SharingStarted.Lazily, "")

    val recentTranslates = channelFlow {
        getRecentTranslatesUseCase().collectLatest {
            send(it)
        }
    }.stateIn(scope = coroutineScope, started = SharingStarted.Eagerly, initialValue = emptyList())

    val savedTranslates = channelFlow {
        getSavedTranslatesUseCase().collectLatest {
            send(it)
        }
    }.stateIn(scope = coroutineScope, started = SharingStarted.Eagerly, initialValue = emptyList())

    private fun sendCopyEvent(text: String) = _screenEvent.tryEmit(DesktopScreenEvent.CopyEvent(text))

    fun onClickTranslatedItem(originalText: String, translatedText: String) {
        coroutineScope.launch {
            when (screenState.value) {
                DesktopScreenState.Recent -> sendCopyEvent(translatedText)
                else -> {
                    addRecentTranslateUseCase(originalText = originalText, translatedText = translatedText)
                    sendCopyEvent(translatedText)
                }
            }
        }
    }

    fun onPreviewKeyEvent(keyEvent: KeyEvent): Boolean {
        val keyEventId = (keyEvent.nativeKeyEvent as java.awt.event.KeyEvent).id
        val (isPressed, isTyped, isReleased) = listOf(
            keyEventId == java.awt.event.KeyEvent.KEY_PRESSED,
            keyEventId == java.awt.event.KeyEvent.KEY_TYPED,
            keyEventId == java.awt.event.KeyEvent.KEY_RELEASED
        )

        when (keyEvent.key) {
            Key.Enter ->
                if (isPressed) {
                    if (keyEvent.isAltPressed) {
                        onAltEnterKeyPressed()
                    } else {
                        onEnterKeyPressed()
                    }
                }

            Key.DirectionUp ->
                if ((isPressed || isTyped) && currentSelectedIndex.value > 0)
                    _currentSelectedIndex.value--

            Key.DirectionDown -> {
                if (
                    (isPressed || isTyped) && currentSelectedIndex.value < when (screenState.value) {
                        DesktopScreenState.Recent -> recentTranslates.value.size - 1
                        DesktopScreenState.Saved -> savedTranslates.value.size - 1
                        else -> 0
                    }
                ) {
                    _currentSelectedIndex.value++
                }
            }

            else -> return false
        }
        return true
    }

    private fun onEnterKeyPressed() {
        coroutineScope.launch {
            commandInference.value.let { command ->
                when (command) {
                    Command.Preferences -> _screenEvent.emit(DesktopScreenEvent.ShowPreferences)
                    Command.Recent -> sendCopyEvent(recentTranslates.value[currentSelectedIndex.value].translatedText)
                    null -> {
                        sendCopyEvent(translatedText.value)
                        addRecentTranslateUseCase(query.value, translatedText.value)
                    }

                    else -> setQuery(command.query)
                }
            }
        }
    }

    private fun onAltEnterKeyPressed() {
        coroutineScope.launch {
            val (query, translatedText) = listOf(query.first(), translatedText.first())

            when {
                query.isNotEmpty() && translatedText.isNotEmpty() ->
                    addSavedTranslateUseCase(
                        originalText = query,
                        translatedText = translatedText
                    )

                commandInference.value == Command.Recent ->
                    recentTranslates
                        .first()
                        .getOrNull(currentSelectedIndex.value)?.let {
                            addSavedTranslateUseCase(originalText = it.originalText, translatedText = it.translatedText)
                        }

                else -> {
                    /*no-op*/
                }
            }
        }
    }

    fun setQuery(query: String) {
        _query.value = query
    }
}