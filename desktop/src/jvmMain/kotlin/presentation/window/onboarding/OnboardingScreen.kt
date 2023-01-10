package presentation.window.onboarding

import androidx.compose.animation.*
import androidx.compose.animation.core.EaseOutExpo
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haeyum.common.presentation.theme.Black
import com.haeyum.common.presentation.theme.ColorBackground
import com.haeyum.common.presentation.theme.ColorLightBlue
import kotlinx.coroutines.delay

@Composable
fun OnboardingScreen() {
    var currentIndex by remember {
        mutableStateOf(-1)
    }
    val increaseCurrentIndex: () -> Unit = remember {
        {
            currentIndex++
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorBackground, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 96.dp, vertical = 40.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            AnimationSlide(currentIndex == 0) {
                SplashSlide()
            }
        }

        Box(modifier = Modifier.padding(top = 96.dp).fillMaxSize(), contentAlignment = Alignment.Center) {
            BasicOnboardingSlide(
                title = "Hello",
                description = "TRANSER is an open-source translation program developed with Kotlin Multiplatform.",
                currentIndex = currentIndex,
                targetIndex = 1,
                onRequestNext = increaseCurrentIndex
            )

            BasicOnboardingSlide(
                title = "Shortcut Permission",
                description = "Please allow permission to run TRANSER with a shortcut at any time.",
                currentIndex = currentIndex,
                targetIndex = 2,
                onRequestNext = increaseCurrentIndex
            )

            BasicOnboardingSlide(
                title = "⌥ + Space",
                description = "Now, you can run TRANSER by pressing the shortcut key. Press it.",
                currentIndex = currentIndex,
                targetIndex = 3,
                onRequestNext = increaseCurrentIndex
            )

            BasicOnboardingSlide(
                title = "Commands",
                description = "You can use commands 'recent', 'saved', and 'preferences' on the search field, just type '>' and type the command.",
                currentIndex = currentIndex,
                targetIndex = 4,
                onRequestNext = increaseCurrentIndex
            )

            BasicOnboardingSlide(
                title = "Recent Command",
                description = "If you type '>recent', you can see the recent translation history.",
                currentIndex = currentIndex,
                targetIndex = 5,
                onRequestNext = increaseCurrentIndex
            )

            BasicOnboardingSlide(
                title = "Saved Command",
                description = "If you type '>saved', you can see the saved translation history.",
                currentIndex = currentIndex,
                targetIndex = 6,
                onRequestNext = increaseCurrentIndex
            )

            BasicOnboardingSlide(
                title = "Preferences Command",
                description = "If you type '>preferences', you can see the preferences window that includes selecting Translation Language.",
                currentIndex = currentIndex,
                targetIndex = 7,
                onRequestNext = increaseCurrentIndex
            )
        }
    }

    LaunchedEffect(Unit) {
        delay(500)
        increaseCurrentIndex()
        delay(2000)
        increaseCurrentIndex()
    }
}

@Composable
private fun AnimationSlide(visible: Boolean, content: @Composable AnimatedVisibilityScope.() -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(
                durationMillis = 1000,
                easing = EaseOutExpo
            )
        ),
        exit = fadeOut() + slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = tween(
                durationMillis = 1000,
                easing = EaseOutExpo
            )
        ),
        content = content
    )
}

@Composable
private fun NextButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = ColorLightBlue)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
            color = Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Light
        )
    }
}

@Composable
private fun SplashSlide() {
    Text(text = "TRANSER", color = Black, fontSize = 28.sp, fontWeight = FontWeight.Thin, letterSpacing = 4.sp)
}

@Composable
private fun BasicOnboardingSlide(
    title: String,
    description: String,
    currentIndex: Int,
    targetIndex: Int,
    onRequestNext: () -> Unit
) {
    AnimationSlide(currentIndex == targetIndex) {
        Box(modifier = Modifier.fillMaxSize()) {
            BasicSlide(
                title = title,
                description = description
            )

            NextButton(
                text = "Next",
                modifier = Modifier.align(Alignment.BottomCenter),
                onClick = onRequestNext
            )
        }
    }
}

@Composable
private fun BasicSlide(title: String, description: String) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            color = Black,
            fontSize = 36.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Text(
            text = description,
            modifier = Modifier.padding(top = 24.dp),
            color = Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center
        )
    }
}