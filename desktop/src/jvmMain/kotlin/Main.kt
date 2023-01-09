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
import com.github.kwhat.jnativehook.GlobalScreen
import com.haeyum.common.domain.model.translation.languages.Language
import com.haeyum.common.domain.usecase.GetPreferencesUseCase
import com.haeyum.common.domain.usecase.SetPreferencesUseCase
import com.haeyum.common.presentation.preferences.PreferencesScreen
import com.haeyum.common.presentation.preferences.PreferencesViewModel
import di.DesktopKoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import org.koin.java.KoinJavaComponent.inject
import presentation.DesktopApp
import presentation.DesktopViewModel
import presentation.TranserShortcutListener
import java.awt.Desktop
import java.awt.desktop.AppForegroundEvent
import java.net.URI

fun main() {
    DesktopKoin.startKoin()
    val viewModel by inject<DesktopViewModel>(DesktopViewModel::class.java)
    val setPreferencesUseCase by inject<SetPreferencesUseCase>(SetPreferencesUseCase::class.java)
    val getPreferencesUseCase by inject<GetPreferencesUseCase>(GetPreferencesUseCase::class.java)

//    System.setProperty("apple.awt.UIElement", "true")

//    val isMac = System.getProperty("os.name").toLowerCase().contains("mac")
    println(System.getProperty("os.name"))

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
        val isForeground by produceState(true) {
            val listener = object: java.awt.desktop.AppForegroundListener {
                override fun appRaisedToForeground(e: AppForegroundEvent?) {
                    value = true
                }

                override fun appMovedToBackground(e: AppForegroundEvent?) {
                    value = false
                }
            }
            java.awt.Desktop.getDesktop().addAppEventListener(listener)

            awaitDispose {
                java.awt.Desktop.getDesktop().removeAppEventListener(listener)
            }
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
                    java.awt.Desktop.getDesktop().requestForeground(true)
                }
            }
        )

//        if (isShowTranslateWindow) {
            Window(
                onCloseRequest = ::exitApplication,
                state = windowState,
                title = "Transer",
                undecorated = true,
                transparent = true,
                resizable = false
            ) {
                DesktopApp(
                    viewModel = viewModel,
                    onShowPreferences = {
                        isShowPreferencesWindow = true
                    }
                )
                MenuBar {
                    Menu(text = "Window") {
                        Item(text = "Hide", shortcut = KeyShortcut(Key.W, meta = true), onClick = {
                            isShowTranslateWindow = false
                        })
                    }
                }
            }
//        }

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
                    supportedLanguages = preferencesViewModel.supportedLanguages.collectAsState().value,
                    selectedSourceLanguage = preferencesViewModel.selectedSourceLanguage.collectAsState().value?.name
                        ?: "-",
                    selectedTargetLanguage = preferencesViewModel.selectedTargetLanguage.collectAsState().value?.name
                        ?: "-",
                    onCloseRequest = {
                        isShowPreferencesWindow = false
                    },
                    onSelectedSourceLanguage = { sourceLanguage ->
                        preferencesViewModel.setSelectedSourceLanguage(sourceLanguage)
                    },
                    onSelectedTargetLanguage = { targetLanguage ->
                        preferencesViewModel.setSelectedTargetLanguage(targetLanguage)
                    },
                    onClickClearData = {
                        preferencesViewModel.clearData()
                    },
                    onClickContact = {
                        Desktop.getDesktop().browse(URI("mailto:vnycall74@naver.com"))
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
            if (getPreferencesUseCase().firstOrNull() == null)
                setPreferencesUseCase(Language("en", "English"), Language("ko", "Korean"))

            java.awt.Desktop.getDesktop().setPreferencesHandler {
                isShowPreferencesWindow = true
            }
        }

        LaunchedEffect(Unit) {
            runCatching {
                GlobalScreen.registerNativeHook()
            }.onSuccess {
                GlobalScreen.addNativeKeyListener(
                    TranserShortcutListener(
                        onEscKeyPressed = {

                        },
                        onTriggerKeyPressed = {
                            if (isForeground && isShowTranslateWindow) {
                                windowState.size = DpSize(width = 0.dp, height = 0.dp)
                                isShowTranslateWindow = false
                            } else {
                                windowState.size = DpSize(width = 720.dp, height = 400.dp)
                                isShowTranslateWindow = true
                                java.awt.Desktop.getDesktop().requestForeground(true)
                            }
                            println("isForeground: $isForeground || isShow: $isShowTranslateWindow")
                        }
                    )
                )
            }.onFailure {
                println("Failed to register native hook")
            }
        }

        LaunchedEffect(Unit) {
            delay(1000)
            java.awt.Desktop.getDesktop().requestForeground(true)
        }
    }
}

object TrayIcon : Painter() {
    override val intrinsicSize: Size = Size(250f, 250f)

    override fun DrawScope.onDraw() {
        drawOval(color = Color.Blue)
    }
}

