package presentation.translation

enum class Command(val state: TranslationScreenState, val query: String) {
    Hint(state = TranslationScreenState.Home, query = ">you can type 'recent', 'saved' or 'preferences'"),
    Guide(state = TranslationScreenState.Home, query = ">guide"),
    Recent(state = TranslationScreenState.Recent, query = ">recent"),
    Saved(state = TranslationScreenState.Saved, query = ">saved"),
    Preferences(state = TranslationScreenState.Home, query = ">preferences"),
//    Undefined(state = DesktopScreenState.Home, query = ">Undefined")
}