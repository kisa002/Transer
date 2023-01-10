package presentation.window

import androidx.compose.runtime.Composable
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import presentation.window.onboarding.OnboardingScreen

@Composable
fun OnboardingWindow(
    state: WindowState = rememberWindowState(
        position = WindowPosition(BiasAlignment(0f, -.3f)),
        size = DpSize(width = 720.dp, height = 400.dp)
    ),
) {
    Window(
        onCloseRequest = { /* TODO */ },
        title = "Onboarding",
        state = state,
        undecorated = true,
        transparent = true,
        resizable = false
    ) {
        OnboardingScreen()
    }
}