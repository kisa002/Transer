package presentation.desktop

sealed class DesktopScreenState {
    object Home : DesktopScreenState()
    object Translate : DesktopScreenState()
    object Recent : DesktopScreenState()
    object Saved : DesktopScreenState()
    data class Error(val errorEvent: DesktopScreenErrorEvent) : DesktopScreenState()
}