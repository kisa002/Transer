package presentation.window.onboarding

import com.github.kwhat.jnativehook.GlobalScreen
import com.haeyum.common.domain.usecase.translation.GetSupportedLanguagesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class OnboardingViewModel(
    private val coroutineScope: CoroutineScope,
    private val getSupportedLanguagesUseCase: GetSupportedLanguagesUseCase
) {
    val supportedLanguages = flow {
        emit(getSupportedLanguagesUseCase(target = "en"))
    }.stateIn(scope = coroutineScope, started = SharingStarted.Eagerly, initialValue = emptyList())
    // TODO INTERNET DISCONNECT RETRY

    fun registerNativeHook() = runCatching {
        GlobalScreen.registerNativeHook()
        true
    }.getOrDefault(false)

    fun onDestroy() {
        coroutineScope.cancel()
    }
}