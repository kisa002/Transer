package supports

object CurrentPlatform {
    val isMac
        get() = System.getProperty("os.name").lowercase().contains("mac")

    val isWindows
        get() = System.getProperty("os.name").lowercase().contains("windows")
}
