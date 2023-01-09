package presentation.desktop

sealed class DesktopScreenEvent {
    object ShowPreferences : DesktopScreenEvent()
    data class CopyEvent(val text: String) : DesktopScreenEvent()
}