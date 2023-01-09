package presentation.desktop

sealed class DesktopScreenErrorEvent {
    object DisconnectedNetwork : DesktopScreenErrorEvent()
    object FailedTranslate : DesktopScreenErrorEvent()
    object NotFoundPreferences : DesktopScreenErrorEvent()
    object WrongCommand : DesktopScreenErrorEvent()
}