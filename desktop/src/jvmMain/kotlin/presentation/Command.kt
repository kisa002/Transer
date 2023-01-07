package presentation

enum class Command(val state: DesktopScreenState, val query: String) {
    Hint(state = DesktopScreenState.Home, query = ">you can type 'recent', 'favorite' or 'preferences'"),
    Guide(state = DesktopScreenState.Home, query = ">guide"),
    Recent(state = DesktopScreenState.Recent, query = ">recent"),
    Favorite(state = DesktopScreenState.Favorite, query = ">favorite"),
    Preferences(state = DesktopScreenState.Home, query = ">preferences"),
//    Undefined(state = DesktopScreenState.Home, query = ">Undefined")
}