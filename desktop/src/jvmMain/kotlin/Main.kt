// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
@file:OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.haeyum.common.presentation.App
import com.haeyum.common.presentation.DesktopViewModel
import com.haeyum.common.presentation.preferences.PreferencesScreen
import com.haeyum.common.presentation.preferences.PreferencesViewModel
import org.koin.java.KoinJavaComponent.inject

fun main() {
    DesktopKoin.startKoin()
    val viewModel by inject<DesktopViewModel>(DesktopViewModel::class.java)

    var players by mutableStateOf(emptyList<String>())
//    val database = TranserDatabaseFactory.getDatabase()

//    TranserDatabaseFactory.getDatabase().preferencesQueries.select().executeAsOneOrNull().let { preferences ->
//        if (preferences == null) {
//            println("Preferences table is null -> Create table")
//            TranserDatabaseFactory.getDatabase().preferencesQueries.insert(nativeLanguage = "Korean", targetLanguage = "English")
//        } else {
//            println(preferences)
//        }
//    }
//    preferencesDesktopViewModel.toString()

    application {
        val trayState = rememberTrayState()
        val windowState = rememberWindowState(
            position = WindowPosition(BiasAlignment(0f, -.3f)),
            size = DpSize(width = 720.dp, height = 400.dp)
        )
        val preferencesWindowState = rememberWindowState(
            position = WindowPosition(BiasAlignment(0f, -.3f)),
            size = DpSize(width = 400.dp, height = 560.dp)
        )
        var isShowTranslateWindow by remember {
            mutableStateOf(true)
        }
        var isShowPreferencesWindow by remember {
            mutableStateOf(false)
        }

        Tray(
            icon = TrayIcon,
            state = trayState,
            tooltip = "Transer",
            onAction = {
                println("onAction")
            },
            menu = {
                Item("Show Transer") {
                    isShowTranslateWindow = true
//                    trayState.sendNotification(Notification(title = "Title", message = LocalDateTime.now().toString(), type = Notification.Type.Info))
                }
            }
        )

        if (isShowTranslateWindow) {
            Window(
                onCloseRequest = ::exitApplication,
                state = windowState,
                title = "Transer",
                undecorated = true,
                transparent = true,
                resizable = false
            ) {
                App(
                    viewModel = viewModel,
                    onMinimize = {
                        windowState.isMinimized = true
                    }
                )
                MenuBar {
                    Menu(text = "File") {
                        Item(
                            text = "Preferences",
                            shortcut = KeyShortcut(Key.Comma, meta = true),
                            onClick = {
                                isShowPreferencesWindow = true
                            }
                        )
                    }
                    Menu(text = "Window") {
                        Item(text = "Hide", shortcut = KeyShortcut(Key.W, meta = true), onClick = {
                            isShowTranslateWindow = false
                        }
                        )
                    }
                }
            }
        }

        if (isShowPreferencesWindow) {
            Window(
                onCloseRequest = {
                    isShowPreferencesWindow = false
                },
                state = preferencesWindowState,
                title = "Preferences",
                undecorated = true,
                transparent = true,
                resizable = false
            ) {
                val preferencesViewModel by remember {
                    inject<PreferencesViewModel>(PreferencesViewModel::class.java)
                }

                PreferencesScreen(
                    supportedLanguages = preferencesViewModel.testLanguageDataset,
                    selectedNativeLanguage = preferencesViewModel.selectedNativeLanguage.collectAsState().value,
                    selectedTargetLanguage = preferencesViewModel.selectedTargetLanguage.collectAsState().value,
                    onCloseRequest = {
                        isShowPreferencesWindow = false
                    },
                    onSelectedNativeLanguage = { nativeLanguage ->
                        preferencesViewModel.setSelectedNativeLanguage(nativeLanguage)
                    },
                    onSelectedTargetLanguage = { targetLanguage ->
                        preferencesViewModel.setSelectedTargetLanguage(targetLanguage)
                    }
                )

                MenuBar {
                    Menu("Window") {
                        Item(
                            text = "Close",
                            onClick = {
                                isShowPreferencesWindow = false
                            },
                            shortcut = KeyShortcut(Key.W, meta = true)
                        )
                    }
                }

                DisposableEffect(Unit) {
                    onDispose {
                        preferencesViewModel.onDestroy()
                    }
                }
            }
        }

        LaunchedEffect(Unit) {
//            database.preferencesQueries.select().asFlow().mapToOneOrNull().collect {
//                println("Main.kt -> Detect : $it}")
//                println("DETECT Main")
//            }
        }
    }
}

object TrayIcon: Painter() {
    override val intrinsicSize: Size = Size(250f, 250f)

    override fun DrawScope.onDraw() {
        drawOval(color = Color.Blue)
    }
}

