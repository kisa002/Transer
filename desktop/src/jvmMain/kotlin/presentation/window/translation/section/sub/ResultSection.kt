package presentation.window.translation.section.sub

import androidx.compose.animation.core.*
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import presentation.component.SectionHeader
import presentation.component.TranslatedItem

@Composable
fun ResultSection(
    query: String,
    translatedText: String,
    isRequesting: Boolean,
    isExistsSavedTranslate: Boolean,
    onClickTranslatedItem: (String, String) -> Unit
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
    val colors: List<Color> = remember {
        listOf(
            Color(0xFF5851D8),
            Color(0xFF833AB4),
            Color(0xFFC13584),
            Color(0xFFE1306C),
            Color(0xFFFD1D1D),
            Color(0xFFF56040),
            Color(0xFFF77737),
            Color(0xFFFCAF45),
            Color(0xFFFFDC80),
            Color(0xFF5851D8)
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SectionHeader("Result")

        if (translatedText.isBlank() || isRequesting) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(top = 96.dp)
                    .size(64.dp)
                    .rotate(rotateAnimation)
                    .border(width = 4.dp, brush = Brush.sweepGradient(colors), shape = CircleShape),
                strokeWidth = 4.dp
            )
        } else {
            TranslatedItem(
                originalText = query,
                translatedText = translatedText,
                isSelected = true,
                isExists = isExistsSavedTranslate,
                modifier = Modifier.padding(12.dp),
                onClick = onClickTranslatedItem
            )
        }
    }
}