package presentation.window.onboarding

import com.github.kwhat.jnativehook.GlobalScreen
import com.haeyum.shared.domain.model.translation.languages.Language
import com.haeyum.shared.domain.usecase.preferences.SetPreferencesUseCase
import com.haeyum.shared.domain.usecase.translation.GetSupportedLanguagesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import supports.CurrentPlatform

class OnboardingViewModel(
    private val coroutineScope: CoroutineScope,
    private val getSupportedLanguagesUseCase: GetSupportedLanguagesUseCase,
    private val setPreferencesUseCase: SetPreferencesUseCase
) {
    private val currentIndex = MutableStateFlow(0)

    val onboardingSlideState = currentIndex.map { currentIndex ->
        OnboardingSlide.values()[currentIndex]
    }.stateIn(coroutineScope, SharingStarted.Eagerly, OnboardingSlide.Empty)

    private val retryGetSupportedLanguagesEvent = MutableSharedFlow<Unit>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val supportedLanguages: StateFlow<List<Language>?> = retryGetSupportedLanguagesEvent
        .transformLatest {
            emit(emptyList())
            emit(
                runCatching { getSupportedLanguagesUseCase(target = "en") }.getOrNull()
            )
        }.stateIn(scope = coroutineScope, started = SharingStarted.Eagerly, initialValue = emptyList())

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

            launch {
                onboardingSlideState
                    .filter { it == OnboardingSlide.RequirePermission }
                    .collectLatest {
                        if (CurrentPlatform.isWindows)
                            increaseCurrentIndex()
                    }
            }
        }

        retryGetSupportedLanguagesEvent()
    }

    fun retryGetSupportedLanguagesEvent() = retryGetSupportedLanguagesEvent.tryEmit(Unit)

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