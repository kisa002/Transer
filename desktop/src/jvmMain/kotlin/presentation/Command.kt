package presentation

enum class Command(val state: DesktopScreenState, val query: String) {
    Guide(state = DesktopScreenState.Home, query = ">Guide"),
    Recent(state = DesktopScreenState.Recent, query = ">Recent"),
    Favorite(state = DesktopScreenState.Favorite, query = ">Favorite"),
    Preferences(state = DesktopScreenState.Home, query = ">Preferences"),
//    Undefined(state = DesktopScreenState.Home, query = ">Undefined")
}