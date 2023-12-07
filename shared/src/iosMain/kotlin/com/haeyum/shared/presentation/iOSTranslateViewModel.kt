import com.haeyum.shared.domain.usecase.recent.AddRecentTranslateUseCase
import com.haeyum.shared.domain.usecase.saved.AddSavedTranslateUseCase
import com.haeyum.shared.domain.usecase.saved.DeleteSavedTranslateUseCase
import com.haeyum.shared.domain.usecase.saved.GetSavedTranslatesUseCase
import com.haeyum.shared.domain.usecase.saved.IsExistsSavedTranslateUseCase
import com.haeyum.shared.domain.usecase.translation.TranslateUseCase
import com.haeyum.shared.extensions.decodeHtmlEntities
import com.haeyum.shared.presentation.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class)
class TranslateViewModel(
    private val translateUseCase: TranslateUseCase,
    private val addRecentTranslateUseCase: AddRecentTranslateUseCase,
    private val isExistsSavedTranslateUseCase: IsExistsSavedTranslateUseCase,
    private val getSavedTranslateUseCase: GetSavedTranslatesUseCase,
    private val addSavedTranslateUseCase: AddSavedTranslateUseCase,
    private val deleteSavedTranslateUseCase: DeleteSavedTranslateUseCase,
) : BaseViewModel() {
    private val _text = MutableStateFlow("")
    val text = _text.asStateFlow()

    val translatedText = text.transformLatest { text ->
        if (text.isNotEmpty()) {
            delay(700)

            val (originalText, translatedText) = text to translateUseCase(text).translatedText.decodeHtmlEntities()
            emit(translatedText)

            addRecentTranslateUseCase(originalText = originalText, translatedText = translatedText)
        } else {
            emit("")
        }
    }.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), initialValue = "")

    private val savedTranslates = channelFlow {
        getSavedTranslateUseCase().collectLatest {
            send(it)
        }
    }.stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = emptyList())

    val isExistsSavedTranslate = translatedText.combine(savedTranslates) { translatedText, savedTranslates ->
        translatedText
    }
        .filterNotNull()
        .flatMapLatest {
            isExistsSavedTranslateUseCase(it)
        }
        .flowOn(Dispatchers.IO)
        .stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = false)

    fun setText(text: String) {
        _text.value = text
    }

    fun clearText() = setText("")

    fun toggleSave() {
        viewModelScope.launch {
            translatedText.first()?.let {
                if (isExistsSavedTranslate.first())
                    deleteSavedTranslateUseCase(it)
                else
                    addSavedTranslateUseCase(text.first(), it)
            }
        }
    }
}