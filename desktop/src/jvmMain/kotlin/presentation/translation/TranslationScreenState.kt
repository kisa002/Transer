package presentation.translation

sealed class TranslationScreenState {
    object Home : TranslationScreenState()
    object Translate : TranslationScreenState()
    object Recent : TranslationScreenState()
    object Saved : TranslationScreenState()
    data class Error(val errorEvent: TranslationScreenErrorEvent) : TranslationScreenState()
}