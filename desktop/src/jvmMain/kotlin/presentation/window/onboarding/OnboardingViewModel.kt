package presentation.window.onboarding

import com.github.kwhat.jnativehook.GlobalScreen
import com.haeyum.common.domain.model.translation.languages.Language
import com.haeyum.common.domain.usecase.preferences.SetPreferencesUseCase
import com.haeyum.common.domain.usecase.translation.GetSupportedLanguagesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val coroutineScope: CoroutineScope,
    private val getSupportedLanguagesUseCase: GetSupportedLanguagesUseCase,
    private val setPreferencesUseCase: SetPreferencesUseCase
) {
    private val currentIndex = MutableStateFlow(0)

    val onboardingSlideState = currentIndex.map { currentIndex ->
        OnboardingSlide.values()[currentIndex]
    }.stateIn(coroutineScope, SharingStarted.Eagerly, OnboardingSlide.Empty)

    val supportedLanguages = flow {
        emit(getSupportedLanguagesUseCase(target = "en"))
    }.stateIn(scope = coroutineScope, started = SharingStarted.Eagerly, initialValue = emptyList())
    // TODO INTERNET DISCONNECT RETRY

    private var selectedSourceLanguage: Language? = null
    private var selectedTargetLanguage: Language? = null

    init {
        coroutineScope.launch {
            launch {
                delay(500)
                increaseCurrentIndex()
                delay(2000)
                increaseCurrentIndex()
            }

            launch {
                onboardingSlideState
                    .filter { it == OnboardingSlide.Done }
                    .onEach { delay(3000) }
                    .collectLatest {
                        GlobalScreen.unregisterNativeHook()
                        setPreferencesUseCase(
                            sourceLanguage = selectedSourceLanguage!!,
                            targetLanguage = selectedTargetLanguage!!
                        )
                    }
            }
        }
    }

    fun registerNativeHook() = runCatching {
        GlobalScreen.registerNativeHook()
        true
    }.getOrDefault(false)

    fun increaseCurrentIndex() = currentIndex.value++

    fun setLanguages(sourceLanguage: Language, targetLanguage: Language) {
        selectedSourceLanguage = sourceLanguage
        selectedTargetLanguage = targetLanguage
    }

    fun onDestroy() {
        coroutineScope.cancel()
    }
}