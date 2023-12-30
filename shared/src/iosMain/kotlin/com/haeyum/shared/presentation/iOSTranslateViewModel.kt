import com.haeyum.shared.domain.usecase.recent.DeleteAndAddRecentTranslateUseCase
import com.haeyum.shared.domain.usecase.saved.AddSavedTranslateUseCase
import com.haeyum.shared.domain.usecase.saved.DeleteSavedTranslateUseCase
import com.haeyum.shared.domain.usecase.saved.GetSavedTranslatesUseCase
import com.haeyum.shared.domain.usecase.saved.IsExistsSavedTranslateUseCase
import com.haeyum.shared.domain.usecase.translation.TranslateUseCase
import com.haeyum.shared.extensions.decodeHtmlEntities
import com.haeyum.shared.presentation.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class TranslateViewModel(
    private val translateUseCase: TranslateUseCase,
    private val deleteAndAddRecentTranslateUseCase: DeleteAndAddRecentTranslateUseCase,
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

            runCatching {
                val (originalText, translatedText) = text to translateUseCase(text).translatedText.decodeHtmlEntities()

                emit(translatedText)
                deleteAndAddRecentTranslateUseCase(originalText = originalText, translatedText = translatedText)
            }.onFailure {
                _snackbarEvent.emit("Please check your network connection.") // TODO: More Separate Error State
            }
        } else {
            emit("")
        }
    }.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), initialValue = "")

    private val savedTranslates = getSavedTranslateUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    val isExistsSavedTranslate = translatedText.combine(savedTranslates) { translatedText, savedTranslates ->
        translatedText
    }
        .filterNotNull()
        .flatMapLatest {
            isExistsSavedTranslateUseCase(it)
        }
        .flowOn(Dispatchers.IO)
        .stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = false)

    private val _snackbarEvent =
        MutableSharedFlow<String>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    val snackbarEvent = _snackbarEvent.asSharedFlow()

    fun setText(text: String) {
        _text.value = text
    }

    fun clearText() = setText("")

    fun toggleSave() {
        viewModelScope.launch {
            translatedText.firstOrNull()?.let {
                if (isExistsSavedTranslate.first())
                    deleteSavedTranslateUseCase(it)
                else
                    addSavedTranslateUseCase(text.first(), it)
            }
        }
    }
}