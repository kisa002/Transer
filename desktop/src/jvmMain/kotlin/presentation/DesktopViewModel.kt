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

    val translateScreenState = combine(isRequesting, query) { isRequesting, query ->
        when {
            query.isEmpty() || (query.length == 1 && query.first() == '>') -> TranslateScreenState.Home
            query == ">Guide".take(query.length) -> TranslateScreenState.Home
            query == ">Recent".take(query.length) -> TranslateScreenState.Recent
            query == ">Favorite".take(query.length) -> TranslateScreenState.Favorite
            query == ">Preferences".take(query.length) -> TranslateScreenState.Home
            query.isNotEmpty() -> TranslateScreenState.Translate
            else -> TranslateScreenState.Home
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = TranslateScreenState.Home
    )

    val commands = listOf(
        ">Guide",
        ">Recent",
        ">Favorite",
        ">Preferences"
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
}