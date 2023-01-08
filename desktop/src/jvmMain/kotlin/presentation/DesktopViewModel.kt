package presentation

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.isAltPressed
import androidx.compose.ui.input.key.key
import com.haeyum.common.domain.usecase.TranslateUseCase
import com.haeyum.common.domain.usecase.recent.AddRecentTranslateUseCase
import com.haeyum.common.domain.usecase.recent.DeleteAndAddRecentTranslateUseCase
import com.haeyum.common.domain.usecase.recent.GetRecentTranslatesUseCase
import com.haeyum.common.domain.usecase.saved.AddSavedTranslateUseCase
import com.haeyum.common.domain.usecase.saved.GetSavedTranslatesUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import java.net.SocketException
import java.nio.channels.UnresolvedAddressException

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalComposeUiApi::class)
class DesktopViewModel(
    private val coroutineScope: CoroutineScope,
    private val translateUseCase: TranslateUseCase,
    private val getRecentTranslatesUseCase: GetRecentTranslatesUseCase,
    private val deleteAndAddRecentTranslateUseCase: DeleteAndAddRecentTranslateUseCase,
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
        }?.let { command ->
            if (query.length <= command.query.length) command
            else null
        }
    }.stateIn(scope = coroutineScope, started = SharingStarted.Lazily, initialValue = null)

    private val errorEvent = MutableSharedFlow<DesktopScreenErrorEvent?>(
        replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val errorState = errorEvent.combine(query) { event, query ->
        event to query
    }.scan(initial = Pair<DesktopScreenErrorEvent?, String?>(null, null)) { previous, current ->
        if (previous.second == null || previous.second == current.second) {
            current.first to current.second
        } else {
            null to current.second
        }
    }.map {
        it.first
    }.stateIn(scope = coroutineScope, started = SharingStarted.Lazily, initialValue = null)

    val screenState = combine(errorState, query, commandInference) { errorState, query, commandInference ->
        when {
            errorState is DesktopScreenErrorEvent -> DesktopScreenState.Error(errorState)
            query.isEmpty() || (query.length == 1 && query.first() == '>') -> DesktopScreenState.Home
            commandInference != null -> commandInference.state
            query.first() == '>' -> DesktopScreenState.Error(DesktopScreenErrorEvent.WrongCommand) // TODO: I think this condition appropriate for move to errorEvent
            query.isNotEmpty() -> DesktopScreenState.Translate
            else -> DesktopScreenState.Home
        }
    }.onEach {
        _currentSelectedIndex.value = 0
    }.stateIn(
        scope = coroutineScope, started = SharingStarted.WhileSubscribed(), initialValue = DesktopScreenState.Home
    )

    private val _screenEvent = MutableSharedFlow<DesktopScreenEvent>(
        replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val screenEvent = _screenEvent.asSharedFlow()

    private val snackbarEvent = MutableSharedFlow<String>(
        replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val snackbarState = snackbarEvent.transformLatest { message ->
        emit(message)
        delay(2000)
        emit(null)
    }.stateIn(scope = coroutineScope, started = SharingStarted.Lazily, initialValue = null)

    private val _currentSelectedIndex = MutableStateFlow(0)
    val currentSelectedIndex = _currentSelectedIndex.asStateFlow()

    val translatedText = query.transformLatest { query ->
        emit(if (query.isBlank() || (query.firstOrNull() == '>')) {
            _isRequesting.value = false
            ""
        } else {
            delay(500)
            _isRequesting.value = true
            delay(500)
            runCatching {
                translateUseCase(q = query, key = "").translatedText
            }.onFailure { exception ->
                if (exception !is CancellationException) errorEvent.emit(
                    when (exception) {
                        is SocketException, is UnresolvedAddressException -> DesktopScreenErrorEvent.DisconnectedNetwork
                        is NullPointerException -> DesktopScreenErrorEvent.NotFoundPreferences // TODO: There is no possibility of an error in the scenario because preferences created on the on-boarding time, but a reset guide logic will be added after monitoring
                        else -> DesktopScreenErrorEvent.FailedTranslate
                    }
                )
            }.getOrDefault("")
        }
        )
    }.map {
        it.replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&").replace("&quot;", "\"")
            .replace("&apos;", "'").replace("&#39;", "'")
    }.onEach {
        _isRequesting.value = false
    }.catch {
        _isRequesting.value = false
        it.printStackTrace()
    }.stateIn(coroutineScope, SharingStarted.Lazily, "")

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

    private fun sendCopyEvent(text: String) = _screenEvent.tryEmit(DesktopScreenEvent.CopyEvent(text)).also {
        sendSnackbarEvent("Copied to clipboard.")
    }

    private fun sendSnackbarEvent(message: String) = snackbarEvent.tryEmit(message)

    fun onClickTranslatedItem(originalText: String, translatedText: String) {
        coroutineScope.launch {
            sendCopyEvent(translatedText)
            deleteAndAddRecentTranslateUseCase(originalText = originalText, translatedText = translatedText)
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
            Key.Enter -> if (isPressed) {
                if (keyEvent.isAltPressed) {
                    onAltEnterKeyPressed()
                } else {
                    onEnterKeyPressed()
                }
            }

            Key.DirectionUp -> if ((isPressed || isTyped) && currentSelectedIndex.value > 0) _currentSelectedIndex.value--

            Key.DirectionDown -> {
                if ((isPressed || isTyped) && currentSelectedIndex.value < when (screenState.value) {
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
                    Command.Recent -> {
                        sendCopyEvent(recentTranslates.value[currentSelectedIndex.value].translatedText)
                        deleteAndAddRecentTranslateUseCase(
                            recentTranslates.value[currentSelectedIndex.value].originalText,
                            recentTranslates.value[currentSelectedIndex.value].translatedText
                        )
                    }

                    Command.Saved -> {
                        sendCopyEvent(savedTranslates.value[currentSelectedIndex.value].translatedText)
                        deleteAndAddRecentTranslateUseCase(
                            savedTranslates.value[currentSelectedIndex.value].originalText,
                            savedTranslates.value[currentSelectedIndex.value].translatedText
                        )
                    }

                    null -> {
                        sendCopyEvent(translatedText.value)
                        deleteAndAddRecentTranslateUseCase(query.value, translatedText.value)
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
                query.isNotEmpty() && translatedText.isNotEmpty() -> addSavedTranslateUseCase(
                    originalText = query, translatedText = translatedText
                )

                commandInference.value == Command.Recent -> recentTranslates.first()
                    .getOrNull(currentSelectedIndex.value)?.let {
                        addSavedTranslateUseCase(originalText = it.originalText, translatedText = it.translatedText)
                    }

                else -> {
                    return@launch
                }
            }

            sendSnackbarEvent("Selected has been saved.")
        }
    }

    fun setQuery(query: String) {
        _query.value = query.removePrefix(" ").removePrefix(" ").replace("\n", "").take(1000)
    }
}