@file:OptIn(ExperimentalComposeUiApi::class)

package presentation.window

import androidx.compose.runtime.*
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.github.kwhat.jnativehook.GlobalScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import presentation.TranserShortcutListener
import presentation.window.translation.TranslationScreen
import presentation.window.translation.TranslationViewModel
import supports.CurrentPlatform
import java.awt.Desktop

@Composable
fun TranslationWindow(
    visible: Boolean,
    title: String,
    state: WindowState = rememberWindowState(
        position = WindowPosition(BiasAlignment(0f, -.3f)),
        size = DpSize(width = 720.dp, height = 400.dp)
    ),
    isForeground: Boolean,
    onChangeVisibleRequest: (Boolean) -> Unit,
    onShowPreferences: () -> Unit,
    onCloseRequest: () -> Unit,
) {
    val isForeground by rememberUpdatedState(isForeground)
    val visible by rememberUpdatedState(visible)
    var alwaysOnTop by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Window(
        onCloseRequest = onCloseRequest,
        state = state,
        title = title,
        undecorated = true,
        transparent = true,
        resizable = false,
        alwaysOnTop = alwaysOnTop
    ) {
        val viewModel by remember {
            KoinJavaComponent.inject<TranslationViewModel>(TranslationViewModel::class.java)
        }

        TranslationScreen(
            viewModel = viewModel,
            onShowPreferences = onShowPreferences,
            alwaysOnTop = alwaysOnTop
        )

        if (CurrentPlatform.isMac) {
            MenuBar {
                Menu(text = "Window") {
                    Item(
                        text = "Hide",
                        shortcut = KeyShortcut(Key.W, meta = true),
                        onClick = {
                            onChangeVisibleRequest(false)
                        }
                    )
                }
            }
        }

        DisposableEffect(Unit) {
            val listener = TranserShortcutListener(
                onEscKeyPressed = {

                },
                onTriggerKeyPressed = {
                    // TODO separate logic and windows platform need to detect foreground event
                    if (((CurrentPlatform.isMac && isForeground) || (CurrentPlatform.isWindows && !state.isMinimized)) && visible) {
                        if (CurrentPlatform.isWindows)
                            state.isMinimized = true

                        onChangeVisibleRequest(false)
                    } else {
                        onChangeVisibleRequest(true)

                        if (Desktop.getDesktop().isSupported(Desktop.Action.APP_REQUEST_FOREGROUND)) {
                            Desktop.getDesktop().requestForeground(true)
                        } else {
                            state.isMinimized = false
                        }
                    }
                }
            )

            runCatching {
                GlobalScreen.registerNativeHook()
            }.onSuccess {
                GlobalScreen.addNativeKeyListener(listener)
            }.onFailure {
                // TODO restart alert.
                println("Failed to register native hook")
            }

            onDispose {
                GlobalScreen.removeNativeKeyListener(listener)
            }
        }

        LaunchedEffect(visible) {
            if (visible) {
                state.size = DpSize(width = 720.dp, height = 400.dp)
            } else {
                state.size = DpSize(width = 0.dp, height = 0.dp)
            }
        }

        LaunchedEffect(isForeground) {
            if (isForeground) {
                state.size = DpSize(width = 720.dp, height = 400.dp)
                onChangeVisibleRequest(true)
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                viewModel.onDestroy()
            }
        }
    }
}