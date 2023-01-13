package supports

object TranserFunctionKey {
    val shortcut
        get() = if (CurrentPlatform.isWindows) "Ctrl + Space" else "⌥ + Space"

    val saveKey
        get() = if (CurrentPlatform.isWindows) "Alt↵" else "⌥↵"
}