package presentation.window

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import org.koin.java.KoinJavaComponent
import presentation.window.onboarding.OnboardingScreen
import presentation.window.onboarding.OnboardingViewModel

@Composable
fun OnboardingWindow(
    visible: Boolean,
    state: WindowState = rememberWindowState(
        position = WindowPosition(BiasAlignment(0f, -.3f)),
        size = DpSize(width = 720.dp, height = 400.dp)
    ),
    onCloseRequest: () -> Unit,
) {
    if (visible) {
        Window(
            onCloseRequest = onCloseRequest,
            title = "Onboarding",
            state = state,
            undecorated = true,
            transparent = true,
            resizable = false
        ) {
            val viewModel by remember {
                KoinJavaComponent.inject<OnboardingViewModel>(OnboardingViewModel::class.java)
            }

            OnboardingScreen(viewModel = viewModel)

            DisposableEffect(Unit) {
                onDispose {
                    viewModel.onDestroy()
                }
            }
        }
    }
}