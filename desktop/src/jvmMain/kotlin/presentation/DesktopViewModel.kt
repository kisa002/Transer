package presentation

import com.haeyum.common.domain.usecase.TranslateUseCase
import com.haeyum.common.domain.usecase.recent.AddRecentTranslateUseCase
import com.haeyum.common.domain.usecase.recent.GetRecentTranslatesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class DesktopViewModel(
    private val coroutineScope: CoroutineScope,
    private val translateUseCase: TranslateUseCase,
    private val getRecentTranslatesUseCase: GetRecentTranslatesUseCase,
    private val addRecentTranslateUseCase: AddRecentTranslateUseCase
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

    fun setQuery(query: String) {
        _query.value = query
    }

    fun onEnterKeyPressed() {
        coroutineScope.launch {
            commandInference.value.let { command ->
                when (command) {
                    Command.Preferences -> _screenEvent.emit(DesktopScreenEvent.ShowPreferences)
                    null -> {
                        _screenEvent.tryEmit(DesktopScreenEvent.CopyEvent(translatedText.value))
                        addRecentTranslateUseCase(query.value, translatedText.value)
                    }

                    else -> setQuery(command.query)
                }
            }
        }
    }
}