@file:OptIn(ExperimentalComposeUiApi::class)

package presentation.window

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.haeyum.common.presentation.preferences.PreferencesScreen
import com.haeyum.common.presentation.preferences.PreferencesViewModel
import org.koin.java.KoinJavaComponent
import java.awt.Desktop
import java.net.URI

@Composable
fun PreferencesWindow(
    visible: Boolean,
    onChangeVisibleRequest: (Boolean) -> Unit,
    windowState: WindowState = rememberWindowState(
        position = WindowPosition(BiasAlignment(0f, -.3f)),
        size = DpSize(width = 400.dp, height = 560.dp)
    )
) {
    if (visible) {
        Window(
            onCloseRequest = {
                onChangeVisibleRequest(false)
            },
            state = windowState,
            title = "Preferences",
            undecorated = true,
            transparent = true,
            resizable = false
        ) {
            val preferencesViewModel by remember {
                KoinJavaComponent.inject<PreferencesViewModel>(PreferencesViewModel::class.java)
            }

            PreferencesScreen(
                supportedLanguages = preferencesViewModel.supportedLanguages.collectAsState().value,
                selectedSourceLanguage = preferencesViewModel.selectedSourceLanguage.collectAsState().value?.name
                    ?: "-",
                selectedTargetLanguage = preferencesViewModel.selectedTargetLanguage.collectAsState().value?.name
                    ?: "-",
                onCloseRequest = {
                    onChangeVisibleRequest(false)
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
                            onChangeVisibleRequest(false)
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
}