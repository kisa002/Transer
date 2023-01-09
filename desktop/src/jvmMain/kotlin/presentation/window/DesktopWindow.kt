@file:OptIn(ExperimentalComposeUiApi::class)

package presentation.window

import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.github.kwhat.jnativehook.GlobalScreen
import org.koin.java.KoinJavaComponent
import presentation.TranserShortcutListener
import presentation.desktop.DesktopApp
import presentation.desktop.DesktopViewModel
import java.awt.Desktop

@Composable
fun DesktopWindow(
    visible: Boolean,
    title: String,
    state: WindowState,
    isForeground: Boolean,
    onChangeVisibleRequest: (Boolean) -> Unit,
    onShowPreferences: () -> Unit,
    onCloseRequest: () -> Unit,
) {
    val isForeground by rememberUpdatedState(isForeground)
    val visible by rememberUpdatedState(visible)

    Window(
        onCloseRequest = onCloseRequest,
        state = state,
        title = title,
        undecorated = true,
        transparent = true,
        resizable = false
    ) {
        val viewModel by KoinJavaComponent.inject<DesktopViewModel>(DesktopViewModel::class.java)

        DesktopApp(
            viewModel = viewModel,
            onShowPreferences = onShowPreferences
        )
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

        DisposableEffect(Unit) {
            val listener = TranserShortcutListener(
                onEscKeyPressed = {

                },
                onTriggerKeyPressed = {
                    if (isForeground && visible) {
                        state.size = DpSize(width = 0.dp, height = 0.dp)
                        onChangeVisibleRequest(false)
                    } else {
                        onChangeVisibleRequest(true)
                        Desktop.getDesktop().requestForeground(true)
                        state.size = DpSize(width = 720.dp, height = 400.dp)
                    }
                }
            )

            runCatching {
                GlobalScreen.registerNativeHook()
            }.onSuccess {
                GlobalScreen.addNativeKeyListener(listener)
            }.onFailure {
                println("Failed to register native hook")
            }

            onDispose {
                GlobalScreen.removeNativeKeyListener(listener)
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