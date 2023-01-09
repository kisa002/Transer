package presentation.translation

sealed class TranslationScreenEvent {
    object ShowPreferences : TranslationScreenEvent()
    data class CopyEvent(val text: String) : TranslationScreenEvent()
}