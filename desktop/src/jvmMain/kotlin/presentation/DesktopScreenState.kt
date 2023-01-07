package presentation

sealed class DesktopScreenState {
    object Home : DesktopScreenState()
    object Translate : DesktopScreenState()
    object Recent : DesktopScreenState()
    object Favorite : DesktopScreenState()
}