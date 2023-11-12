import com.haeyum.shared.domain.usecase.preferences.GetPreferencesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.awt.Desktop
import java.awt.desktop.AppForegroundEvent
import java.awt.desktop.AppForegroundListener

class MainViewModel(
    coroutineScope: CoroutineScope,
    getPreferencesUseCase: GetPreferencesUseCase
) {
    private val _visibleOnboardingWindow = MutableStateFlow(false)
    private val _visibleTranslationWindow = MutableStateFlow(false)
    private val _visiblePreferencesWindow = MutableStateFlow(false)

    val visibleOnboardingWindow = _visibleOnboardingWindow.asStateFlow()
    val visibleTranslationWindow = _visibleTranslationWindow.asStateFlow()
    val visiblePreferencesWindow = _visiblePreferencesWindow.asStateFlow()

    val isExistsPreferences = channelFlow {
        getPreferencesUseCase().collectLatest {
            send(it != null)
        }
    }.stateIn(coroutineScope, SharingStarted.Eagerly, null)

    val isForeground = callbackFlow {
        val listener = object : AppForegroundListener {
            override fun appRaisedToForeground(e: AppForegroundEvent?) {
                trySend(true)
            }

            override fun appMovedToBackground(e: AppForegroundEvent?) {
                trySend(false)
            }
        }
        Desktop.getDesktop().addAppEventListener(listener)

        awaitClose {
            Desktop.getDesktop().removeAppEventListener(listener)
        }
    }.stateIn(coroutineScope, SharingStarted.Eagerly, true)

    init {
        coroutineScope.launch {
            isExistsPreferences
                .filterNotNull()
                .onEach { isExistsPreferences ->
                    if (isExistsPreferences) {
                        setVisibleTranslationWindow(true)
                    } else {
                        setVisibleOnboardingWindow(true)
                    }
                }
                .filter { it }
                .collectLatest {
                    setVisibleOnboardingWindow(false)
                    setVisibleTranslationWindow(true)
                }
        }

    }

    fun setVisibleOnboardingWindow(value: Boolean) {
        _visibleOnboardingWindow.value = value
    }

    fun setVisibleTranslationWindow(value: Boolean) {
        _visibleTranslationWindow.value = value
    }

    fun setVisiblePreferencesWindow(value: Boolean) {
        _visiblePreferencesWindow.value = value
    }
}