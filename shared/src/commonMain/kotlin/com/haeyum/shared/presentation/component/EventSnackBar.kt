package presentation.component

import androidx.compose.animation.*
import androidx.compose.animation.core.EaseOutExpo
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.haeyum.shared.presentation.theme.ColorLightBlue
import com.haeyum.shared.presentation.theme.White

@Composable
fun BoxScope.EventSnackBar(snackbarState: String?) {
    AnimatedVisibility(
        visible = snackbarState != null,
        modifier = Modifier.align(Alignment.BottomCenter),
        enter = fadeIn() + slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(
                durationMillis = 1000,
                easing = EaseOutExpo
            )
        ),
        exit = fadeOut() + slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(
                durationMillis = 1000,
                easing = EaseOutExpo
            )
        )
    ) {
        Snackbar(backgroundColor = ColorLightBlue) {
            Text(
                text = snackbarState ?: "",
                color = White,
                fontSize = 16.sp
            )
        }
    }
}
