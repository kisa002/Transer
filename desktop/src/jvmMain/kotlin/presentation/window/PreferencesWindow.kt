package presentation.window

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import com.haeyum.shared.presentation.component.Header
import com.haeyum.shared.presentation.preferences.PreferencesScreen
import com.haeyum.shared.presentation.preferences.PreferencesViewModel
import com.haeyum.shared.presentation.theme.ColorSecondaryDivider
import com.haeyum.shared.presentation.theme.White
import org.koin.java.KoinJavaComponent
import supports.CurrentPlatform
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
            val preferencesViewModel: PreferencesViewModel by produceState(
                initialValue = KoinJavaComponent.get(
                    PreferencesViewModel::class.java
                )
            ) {
                awaitDispose(value::onCleared)
            }

            PreferencesScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = White)
                    .border(
                        width = 1.dp,
                        color = ColorSecondaryDivider,
                        shape = RoundedCornerShape(8.dp)
                    ),
                header = {
                    Header(
                        title = "Preferences",
                        imageVector = Icons.Default.Close,
                        onClick = {
                            onChangeVisibleRequest(false)
                        }
                    )
                },
                supportedLanguages = preferencesViewModel.supportedLanguages.collectAsState().value,
                selectedSourceLanguage = preferencesViewModel.selectedSourceLanguage.collectAsState().value?.name
                    ?: "-",
                selectedTargetLanguage = preferencesViewModel.selectedTargetLanguage.collectAsState().value?.name
                    ?: "-",
                onSelectedSourceLanguage = preferencesViewModel::setSelectedSourceLanguage,
                onSelectedTargetLanguage = preferencesViewModel::setSelectedTargetLanguage,
                onClickClearData = preferencesViewModel::clearData,
                onClickContact = {
                    Desktop.getDesktop().browse(URI("mailto:vnycall74@naver.com"))
                }
            )

            if (CurrentPlatform.isMac) {
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
            }
        }
    }
}