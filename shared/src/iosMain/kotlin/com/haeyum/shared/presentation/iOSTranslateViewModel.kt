import com.haeyum.shared.domain.usecase.recent.AddRecentTranslateUseCase
import com.haeyum.shared.domain.usecase.translation.TranslateUseCase
import com.haeyum.shared.presentation.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class)
class TranslateViewModel(
    private val translateUseCase: TranslateUseCase,
    private val addRecentTranslateUseCase: AddRecentTranslateUseCase
) : BaseViewModel() {
    private val _text = MutableStateFlow("")
    val text = _text.asStateFlow()

    val translatedText = text.transformLatest { text ->
        if (text.isNotEmpty()) {
            delay(700)

            val (originalText, translatedText) = text to translateUseCase(text).translatedText
            emit(translatedText)

            addRecentTranslateUseCase(originalText = originalText, translatedText = translatedText)
        } else {
            emit("")
        }
    }.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), initialValue = "")
    
    fun setText(text: String) {
        _text.value = text
    }
}