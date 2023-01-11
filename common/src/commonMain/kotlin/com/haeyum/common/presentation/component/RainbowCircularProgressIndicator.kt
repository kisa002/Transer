package com.haeyum.common.presentation.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import com.haeyum.common.presentation.theme.ColorsLoading

@Composable
fun RainbowCircularProgressIndicator(
    modifier: Modifier = Modifier,
    strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotateAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 720,
                easing = LinearEasing
            )
        )
    )
    val colors = remember {
        ColorsLoading
    }

    CircularProgressIndicator(
        modifier = modifier
            .rotate(rotateAnimation)
            .border(width = strokeWidth, brush = Brush.sweepGradient(colors), shape = CircleShape),
        strokeWidth = strokeWidth
    )
}