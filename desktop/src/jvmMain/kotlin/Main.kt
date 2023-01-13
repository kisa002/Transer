// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.application
import com.haeyum.common.presentation.theme.TranserTheme
import di.DesktopKoin
import org.koin.java.KoinJavaComponent.inject
import presentation.window.OnboardingWindow
import presentation.window.PreferencesWindow
import presentation.window.TranslationWindow
import java.awt.Desktop

fun main() {
    DesktopKoin.startKoin()
    val viewModel by inject<MainViewModel>(MainViewModel::class.java)

//    System.setProperty("apple.awt.UIElement", "true")
//    val isMac = System.getProperty("os.name").toLowerCase().contains("mac")
    println(System.getProperty("os.name"))

    application {
        TranserTheme {
            val visibleOnboardingWindow by viewModel.visibleOnboardingWindow.collectAsState()
            val visibleTranslationWindow by viewModel.visibleTranslationWindow.collectAsState()
            val visiblePreferencesWindow by viewModel.visiblePreferencesWindow.collectAsState()

            val isForeground = viewModel.isForeground.collectAsState().value
            val isExistsPreferences by viewModel.isExistsPreferences.collectAsState()

            OnboardingWindow(
                visible = visibleOnboardingWindow,
                onCloseRequest = ::exitApplication
            )

            if (isExistsPreferences == true) {
                TranslationWindow(
                    visible = visibleTranslationWindow,
                    title = "Transer",
                    isForeground = isForeground,
                    onChangeVisibleRequest = viewModel::setVisibleTranslationWindow,
                    onShowPreferences = {
                        viewModel.setVisiblePreferencesWindow(true)
                    },
                    onCloseRequest = ::exitApplication
                )
            }

            PreferencesWindow(
                visible = visiblePreferencesWindow,
                onChangeVisibleRequest = viewModel::setVisiblePreferencesWindow
            )

            LaunchedEffect(Unit) {
                Desktop.getDesktop().setPreferencesHandler {
                    viewModel.setVisiblePreferencesWindow(true)
                }
            }
        }
    }
}

