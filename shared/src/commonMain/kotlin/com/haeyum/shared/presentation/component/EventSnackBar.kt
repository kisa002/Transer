package com.haeyum.shared.presentation.component

import androidx.compose.animation.*
import androidx.compose.animation.core.EaseOutExpo
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.sp
import com.haeyum.shared.presentation.theme.White

@Composable
fun BoxScope.EventSnackBar(
    shape: Shape = MaterialTheme.shapes.small,
    backgroundColor: Color,
    eventSnackbarState: EventSnackbarState
) {
    AnimatedVisibility(
        visible = eventSnackbarState.visibility.collectAsState().value,
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
        Snackbar(shape = shape, backgroundColor = backgroundColor) {
            Text(
                text = eventSnackbarState.message.collectAsState().value ?: "-",
                color = White,
                fontSize = 16.sp
            )
        }
    }
}
