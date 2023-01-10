package presentation.window.onboarding

import com.github.kwhat.jnativehook.GlobalScreen

class OnboardingViewModel {
    fun registerNativeHook() = runCatching {
        GlobalScreen.registerNativeHook()
        true
    }.getOrDefault(false)
}