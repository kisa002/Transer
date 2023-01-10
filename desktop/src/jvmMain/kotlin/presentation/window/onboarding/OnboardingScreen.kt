package presentation.window.onboarding

import androidx.compose.animation.*
import androidx.compose.animation.core.EaseOutExpo
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haeyum.common.domain.model.translation.languages.Language
import com.haeyum.common.presentation.theme.*
import kotlinx.coroutines.delay
import org.koin.java.KoinJavaComponent.inject

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
    // TODO dispose
    val viewModel by remember {
        inject<OnboardingViewModel>(OnboardingViewModel::class.java)
    }

    val supportedLanguages by viewModel.supportedLanguages.collectAsState()

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

            ShortcutOnboardingSlide(
                viewModel = viewModel,
                currentIndex = currentIndex,
                targetIndex = 3,
                onRequestNext = increaseCurrentIndex
            )

            BasicOnboardingSlide(
                title = "⌥ + Space",
                description = "Now, you can run TRANSER by pressing the shortcut key.",
                currentIndex = currentIndex,
                targetIndex = 4,
                onRequestNext = increaseCurrentIndex
            )

            BasicOnboardingSlide(
                title = "Commands",
                description = "You can use commands 'recent', 'saved', and 'preferences' on the search field, just type '>' and type the command.",
                currentIndex = currentIndex,
                targetIndex = 5,
                onRequestNext = increaseCurrentIndex
            )

            BasicOnboardingSlide(
                title = "Recent Command",
                description = "If you type '>recent', you can see the recent translation history.",
                currentIndex = currentIndex,
                targetIndex = 6,
                onRequestNext = increaseCurrentIndex
            )

            BasicOnboardingSlide(
                title = "Saved Command",
                description = "If you type '>saved', you can see the saved translation history.",
                currentIndex = currentIndex,
                targetIndex = 7,
                onRequestNext = increaseCurrentIndex
            )

            BasicOnboardingSlide(
                title = "Preferences Command",
                description = "If you type '>preferences', you can see the preferences window that includes selecting Translation Language.",
                currentIndex = currentIndex,
                targetIndex = 8,
                onRequestNext = increaseCurrentIndex
            )
        }

        SelectLanguageOnboarding(
            supportedLanguages = supportedLanguages,
            currentIndex = currentIndex,
            targetIndex = 2,
            onRequestNext = increaseCurrentIndex
        )
    }

    LaunchedEffect(Unit) {
        delay(500)
        increaseCurrentIndex()
        delay(2000)
        increaseCurrentIndex()
    }
}

@Composable
private fun ShortcutOnboardingSlide(
    viewModel: OnboardingViewModel,
    currentIndex: Int,
    targetIndex: Int,
    onRequestNext: () -> Unit
) {
    BasicOnboardingSlide(
        title = "Shortcut Permission",
        description = "Please allow permission to run TRANSER with a shortcut at any time.",
        currentIndex = currentIndex,
        targetIndex = targetIndex,
        onRequestNext = {
            if (viewModel.registerNativeHook()) {
                onRequestNext()
            }
        }
    )
}

@Composable
private fun SelectLanguageOnboarding(
    supportedLanguages: List<Language>,
    currentIndex: Int,
    targetIndex: Int,
    onRequestNext: () -> Unit
) {
    AnimationSlide(currentIndex == targetIndex) {
        var expandedSourceLanguage by remember {
            mutableStateOf(false)
        }

        var expandedTargetLanguage by remember {
            mutableStateOf(false)
        }

        var selectedSourceLanguage by remember {
            mutableStateOf<Language?>(null)
        }

        var selectedTargetLanguage by remember {
            mutableStateOf<Language?>(null)
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Select Language",
                    color = Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Please select the language you want to translate.",
                    modifier = Modifier.padding(top = 4.dp),
                    color = Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.fillMaxWidth().weight(1f).padding(top = 18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SelecetLanguageSection(
                        subject = "Source",
                        isSubjectLeft = false,
                        supportedLanguages = supportedLanguages,
                        expanded = expandedSourceLanguage,
                        onChangeExpanded = {
                            expandedSourceLanguage = it
                        },
                        selectedLanguage = selectedSourceLanguage,
                        onSelectedLanguage = {
                            selectedSourceLanguage = it
                        }
                    )
                    SelecetLanguageSection(
                        subject = "Target",
                        isSubjectLeft = true,
                        supportedLanguages = supportedLanguages,
                        expanded = expandedTargetLanguage,
                        onChangeExpanded = {
                            expandedTargetLanguage = it
                        },
                        selectedLanguage = selectedTargetLanguage,
                        onSelectedLanguage = {
                            selectedTargetLanguage = it
                        }
                    )
                }
            }
            // TODO enable, disable
            NextButton(text = "Next", modifier = Modifier.align(Alignment.BottomCenter), onClick = onRequestNext)
        }
    }
}

@Composable
private fun SelecetLanguageSection(
    subject: String,
    isSubjectLeft: Boolean,
    supportedLanguages: List<Language>,
    expanded: Boolean,
    onChangeExpanded: (Boolean) -> Unit,
    selectedLanguage: Language?,
    onSelectedLanguage: (Language) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (isSubjectLeft) {
            Text(
                text = subject,
                color = ColorText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(8.dp))
        }
        OutlinedButton(
            onClick = { onChangeExpanded(true) },
            modifier = Modifier.width(180.dp),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(width = (1.5).dp, color = ColorSelected),
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Transparent)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedLanguage?.name ?: "Select Language",
                    color = ColorText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 2.dp),
                    tint = ColorIcon
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onChangeExpanded(false) },
            modifier = Modifier.background(color = ColorBackground)
                .size(width = 180.dp, height = 160.dp),
            offset = DpOffset(x = if (isSubjectLeft) (48).dp else (0).dp, y = 0.dp)
        ) {
            LazyColumn(
                modifier = Modifier.background(color = ColorBackground)
                    .size(width = 180.dp, height = 160.dp)
            ) {
                items(supportedLanguages) {
                    DropdownMenuItem(
                        onClick = {
                            onSelectedLanguage(it)
                        },
                        modifier = Modifier.background(color = ColorBackground)
                    ) {
                        Text(
                            text = it.name,
                            color = Black,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
        if (!isSubjectLeft) {
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = subject,
                color = ColorText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
        }
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
            modifier = Modifier.padding(horizontal = 24.dp),
            color = Black,
            fontSize = 14.sp,
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