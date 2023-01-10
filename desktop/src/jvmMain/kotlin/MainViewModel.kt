import com.haeyum.common.domain.model.translation.languages.Language
import com.haeyum.common.domain.usecase.preferences.GetPreferencesUseCase
import com.haeyum.common.domain.usecase.preferences.SetPreferencesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.awt.Desktop
import java.awt.desktop.AppForegroundEvent
import java.awt.desktop.AppForegroundListener

class MainViewModel(
    coroutineScope: CoroutineScope,
    setPreferencesUseCase: SetPreferencesUseCase,
    getPreferencesUseCase: GetPreferencesUseCase
) {
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
            if (getPreferencesUseCase().firstOrNull() == null)
                setPreferencesUseCase(Language("en", "English"), Language("ko", "Korean"))
        }
    }
}