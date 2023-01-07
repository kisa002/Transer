package presentation

enum class Command(val state: DesktopScreenState, val query: String) {
    Hint(state = DesktopScreenState.Home, query = ">you can type 'recent', 'saved' or 'preferences'"),
    Guide(state = DesktopScreenState.Home, query = ">guide"),
    Recent(state = DesktopScreenState.Recent, query = ">recent"),
    Saved(state = DesktopScreenState.Saved, query = ">saved"),
    Preferences(state = DesktopScreenState.Home, query = ">preferences"),
//    Undefined(state = DesktopScreenState.Home, query = ">Undefined")
}