package presentation.window.translation

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.isAltPressed
import androidx.compose.ui.input.key.key
import com.haeyum.shared.domain.usecase.recent.DeleteAndAddRecentTranslateUseCase
import com.haeyum.shared.domain.usecase.recent.GetRecentTranslatesUseCase
import com.haeyum.shared.domain.usecase.saved.AddSavedTranslateUseCase
import com.haeyum.shared.domain.usecase.saved.DeleteSavedTranslateUseCase
import com.haeyum.shared.domain.usecase.saved.GetSavedTranslatesUseCase
import com.haeyum.shared.domain.usecase.saved.IsExistsSavedTranslateUseCase
import com.haeyum.shared.domain.usecase.translation.TranslateUseCase
import com.haeyum.shared.extensions.decodeHtmlEntities
import io.ktor.util.network.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import java.net.SocketException

@OptIn(ExperimentalCoroutinesApi::class)
class TranslationViewModel(
    private val coroutineScope: CoroutineScope,
    private val translateUseCase: TranslateUseCase,
    private val getRecentTranslatesUseCase: GetRecentTranslatesUseCase,
    private val deleteAndAddRecentTranslateUseCase: DeleteAndAddRecentTranslateUseCase,
    private val getSavedTranslatesUseCase: GetSavedTranslatesUseCase,
    private val addSavedTranslateUseCase: AddSavedTranslateUseCase,
    private val deleteSavedTranslateUseCase: DeleteSavedTranslateUseCase,
    private val isExistsSavedTranslateUseCase: IsExistsSavedTranslateUseCase
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

    private val errorEvent = MutableSharedFlow<TranslationScreenErrorEvent?>(
        replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val errorState = errorEvent.combine(query) { event, query ->
        event to query
    }.scan(initial = Pair<TranslationScreenErrorEvent?, String?>(null, null)) { previous, current ->
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
            errorState is TranslationScreenErrorEvent -> TranslationScreenState.Error(errorState)
            query.isEmpty() || (query.length == 1 && query.first() == '>') -> TranslationScreenState.Home
            commandInference != null -> commandInference.state
            query.first() == '>' -> TranslationScreenState.Error(TranslationScreenErrorEvent.WrongCommand) // TODO: I think this condition appropriate for move to errorEvent
            query.isNotEmpty() -> TranslationScreenState.Translate
            else -> TranslationScreenState.Home
        }
    }.onEach {
        _currentSelectedIndex.value = 0
    }.stateIn(
        scope = coroutineScope, started = SharingStarted.WhileSubscribed(), initialValue = TranslationScreenState.Home
    )

    private val _screenEvent = MutableSharedFlow<TranslationScreenEvent>(
        replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val screenEvent = _screenEvent.asSharedFlow()

    val snackbarEvent = MutableSharedFlow<String>(
        replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val _currentSelectedIndex = MutableStateFlow(0)
    val currentSelectedIndex = _currentSelectedIndex.asStateFlow()

    val translatedText = query.transformLatest { query ->
        emit(
            if (query.isBlank() || (query.firstOrNull() == '>')) {
                _isRequesting.value = false
                ""
            } else {
                delay(500)
                _isRequesting.value = true
                delay(500)
                runCatching {
                    translateUseCase(q = query).translatedText
                }.onFailure { exception ->
                    if (exception !is CancellationException) errorEvent.emit(
                        when (exception) {
                            is SocketException, is UnresolvedAddressException -> TranslationScreenErrorEvent.DisconnectedNetwork
                            is NullPointerException -> TranslationScreenErrorEvent.NotFoundPreferences // TODO: There is no possibility of an error in the scenario because preferences created on the on-boarding time, but a reset guide logic will be added after monitoring
                            else -> TranslationScreenErrorEvent.FailedTranslate
                        }
                    )
                }.getOrDefault("")
            }
        )
    }
        .map(String::decodeHtmlEntities)
        .onEach {
            _isRequesting.value = false
        }.catch {
            _isRequesting.value = false
            it.printStackTrace()
        }.stateIn(coroutineScope, SharingStarted.Lazily, "")

    val recentTranslates = getRecentTranslatesUseCase().stateIn(
        scope = coroutineScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    val savedTranslates = getSavedTranslatesUseCase().stateIn(
        scope = coroutineScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    val isExistsSavedTranslate = combine(
        screenState,
        translatedText,
        currentSelectedIndex,
        savedTranslates
    ) { state, text, index, _ ->
        Triple(state, text, index)
    }.flatMapLatest { (state, text, index) ->
        when (state) {
            TranslationScreenState.Recent -> {
                recentTranslates.first().getOrNull(index)?.let { recent ->
                    isExistsSavedTranslateUseCase(recent.translatedText)
                } ?: flowOf(false)
            }

            TranslationScreenState.Saved -> {
                savedTranslates.first().getOrNull(index)?.let { saved ->
                    isExistsSavedTranslateUseCase(saved.translatedText)
                } ?: flowOf(false)
            }

            else -> isExistsSavedTranslateUseCase(text)
        }
    }.stateIn(scope = coroutineScope, started = SharingStarted.Lazily, initialValue = false)

    val protectOutOfCurrentIndex = combine(
        currentSelectedIndex,
        savedTranslates.map { it.size },
        screenState,
    ) { currentSelectedIndex, size, screenState ->
        if (screenState == TranslationScreenState.Saved)
            if (currentSelectedIndex > 0 && currentSelectedIndex >= size - 1) {
                _currentSelectedIndex.value = size - 1
            }
    }.launchIn(coroutineScope)

    private fun sendCopyEvent(text: String) = _screenEvent.tryEmit(TranslationScreenEvent.CopyEvent(text)).also {
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
                        TranslationScreenState.Recent -> recentTranslates.value.size - 1
                        TranslationScreenState.Saved -> savedTranslates.value.size - 1
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
                    Command.Preferences -> _screenEvent.emit(TranslationScreenEvent.ShowPreferences)
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
                        if (translatedText.first().isNotEmpty()) {
                            sendCopyEvent(translatedText.value)
                            deleteAndAddRecentTranslateUseCase(query.value, translatedText.value)
                        }
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
                    addOrDeleteSavedTranslate(originalText = query, translatedText = translatedText)

                commandInference.value == Command.Recent ->
                    recentTranslates.first()
                        .getOrNull(currentSelectedIndex.value)?.let {
                            addOrDeleteSavedTranslate(
                                originalText = it.originalText,
                                translatedText = it.translatedText
                            )
                        }

                commandInference.value == Command.Saved ->
                    savedTranslates.first()
                        .getOrNull(currentSelectedIndex.value)?.let {
                            addOrDeleteSavedTranslate(
                                originalText = it.originalText,
                                translatedText = it.translatedText
                            )
                        }
            }
        }
    }

    private suspend fun addOrDeleteSavedTranslate(originalText: String, translatedText: String) {
        if (isExistsSavedTranslate.first()) {
            deleteSavedTranslateUseCase(translatedText)
            sendSnackbarEvent("The saved has been deleted.")
        } else {
            addSavedTranslateUseCase(originalText = originalText, translatedText = translatedText)
            sendSnackbarEvent("The selected has been saved.")
        }
    }

    fun setQuery(query: String) {
        _query.value = query.removePrefix(" ").removePrefix(" ").replace("\n", "").take(1000)
    }

    fun onDestroy() {
        coroutineScope.cancel()
    }
}