// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.runtime.*
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import di.DesktopKoin
import org.koin.java.KoinJavaComponent.inject
import presentation.window.TranslationWindow
import presentation.window.PreferencesWindow
import java.awt.Desktop

fun main() {
    DesktopKoin.startKoin()
    val viewModel by inject<MainViewModel>(MainViewModel::class.java)

//    System.setProperty("apple.awt.UIElement", "true")
//    val isMac = System.getProperty("os.name").toLowerCase().contains("mac")
    println(System.getProperty("os.name"))

    application {
        var visibleTranslationWindow by remember {
            mutableStateOf(true)
        }
        var visiblePreferencesWindow by remember {
            mutableStateOf(false)
        }

        val isForeground = viewModel.isForeground.collectAsState().value

        TranslationWindow(
            visible = visibleTranslationWindow,
            title = "Transer",
            isForeground = isForeground,
            onChangeVisibleRequest = {
                visibleTranslationWindow = it
            },
            onShowPreferences = {
                visiblePreferencesWindow = true
            },
            onCloseRequest = ::exitApplication
        )

        PreferencesWindow(
            visible = visiblePreferencesWindow,
            onChangeVisibleRequest = {
                visiblePreferencesWindow = it
            }
        )

        LaunchedEffect(Unit) {
            Desktop.getDesktop().setPreferencesHandler {
                visiblePreferencesWindow = true
            }
        }
    }
}

