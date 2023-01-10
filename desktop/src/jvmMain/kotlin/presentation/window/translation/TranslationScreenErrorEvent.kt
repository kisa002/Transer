package presentation.window.translation

sealed class TranslationScreenErrorEvent {
    object DisconnectedNetwork : TranslationScreenErrorEvent()
    object FailedTranslate : TranslationScreenErrorEvent()
    object NotFoundPreferences : TranslationScreenErrorEvent()
    object WrongCommand : TranslationScreenErrorEvent()
}