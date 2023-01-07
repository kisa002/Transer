package presentation

import com.haeyum.common.domain.usecase.GetSupportedLanguagesUseCase
import com.haeyum.common.domain.usecase.TranslateUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class)
class DesktopViewModel(
    private val coroutineScope: CoroutineScope,
    private val translateUseCase: TranslateUseCase,
    private val getSupportedLanguagesUseCase: GetSupportedLanguagesUseCase
) {
    private val _isRequesting = MutableStateFlow(false)
    val isRequesting: StateFlow<Boolean> = _isRequesting

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    val commandInference = query.map { query ->
        Command.values().firstOrNull { command ->
            query.contains(command.query.take(2))
        }
    }.stateIn(scope = coroutineScope, started = SharingStarted.Lazily, initialValue = null)

    val translateScreenState = combine(isRequesting, query, commandInference) { isRequesting, query, commandInference ->
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

    fun setQuery(query: String) {
        _query.value = query
    }

    fun onEnterKeyPressed() {
        // Guide
        when {
            translateScreenState.value == DesktopScreenState.Translate && translatedText.value.isNotEmpty() -> {
                "COPY"
            }

            translateScreenState.value == DesktopScreenState.Recent -> {
                "RECENT"
            }

            translateScreenState.value == DesktopScreenState.Favorite -> {
                "FAVORITE"
            }

            else -> {
                "NONE"
            }
        }
        if (query.value == ">Preferences".take(query.value.length)) {

        }
    }
}