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
import org.koin.java.KoinJavaComponent
import presentation.TranserShortcutListener
import presentation.window.translation.TranslationScreen
import presentation.window.translation.TranslationViewModel
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

    Window(
        onCloseRequest = onCloseRequest,
        state = state,
        title = title,
        undecorated = true,
        transparent = true,
        resizable = false
    ) {
        val viewModel by remember {
            KoinJavaComponent.inject<TranslationViewModel>(TranslationViewModel::class.java)
        }

        TranslationScreen(
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
                        onChangeVisibleRequest(false)
                    } else {
                        onChangeVisibleRequest(true)
                        Desktop.getDesktop().requestForeground(true)
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