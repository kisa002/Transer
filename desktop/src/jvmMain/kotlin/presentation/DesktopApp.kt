@file:OptIn(ExperimentalComposeUiApi::class)

package presentation

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun App(viewModel: DesktopViewModel, onShowPreferences: () -> Unit = {}, onMinimize: () -> Unit = {}) {
    val query by viewModel.query.collectAsState()
    val translatedText by viewModel.translatedText.collectAsState()
    val isRequesting by viewModel.isRequesting.collectAsState()

    val focusRequester = remember { FocusRequester() }
    val infiniteTransition = rememberInfiniteTransition()

    val clipboardManager = LocalClipboardManager.current
    val focusManager = LocalFocusManager.current

    val desktopScreenState by viewModel.screenState.collectAsState()
    val commandInference by viewModel.commandInference.collectAsState()

    val coroutineScope = rememberCoroutineScope()

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFEFEFEF), shape = RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(57.dp).padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = query,
                onValueChange = { viewModel.setQuery(it.take(1000)) },
                modifier = Modifier.weight(1f).focusRequester(focusRequester).onPreviewKeyEvent { keyEvent ->
                    when (keyEvent.key) {
                        Key.Enter -> {
                            viewModel.onEnterKeyPressed()
                        }

                        Key.DirectionUp -> {
//                            viewModel.onUpKeyPressed()
                        }

                        Key.DirectionDown -> {
//                            viewModel.onDownKeyPressed()
                        }

                        else -> {
                            return@onPreviewKeyEvent false
                        }
                    }
                    true
                },
                textStyle = TextStyle(color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Medium),
                singleLine = true,
                maxLines = 1,
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.fillMaxWidth()) {
                        innerTextField()
                    }

                    Row(modifier = Modifier.fillMaxWidth()) {
                        if (query.isEmpty()) {
                            Text(
                                text = "Enter text to translate...",
                                style = TextStyle(
                                    color = Color(0xFF999999),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        } else {
                            commandInference?.query?.let { command ->
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(Color.Black)) {
                                            append(query)
                                        }
                                        append(command.lowercase().removePrefix(query.lowercase()))
                                    },
                                    style = TextStyle(
                                        color = Color(0xFF999999),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            } ?: Text(
                                text = query,
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
            )
        }
        Divider(modifier = Modifier, color = Color(0xFFAFAFAF), thickness = (0.5).dp)
        when (desktopScreenState) {
            DesktopScreenState.Home -> {
                Text(
                    text = "Guide",
                    modifier = Modifier.padding(top = 12.dp).padding(horizontal = 18.dp),
                    style = TextStyle(color = Color(0xFF3F8CFF), fontSize = 16.sp, fontWeight = FontWeight.Medium)
                )
                Text(
                    text = buildAnnotatedString {
                        append("Commands can use through")
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append(" >\n")
                        }
                        append("If you want to show or hide this guide, you can enter ")
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append(">Guide")
                        }
                        append(" to toggle it on/off.\n\n")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(">Recent\n")
                        }
                        append("Shows recent translation results.\n\n")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(">Favorite\n")
                        }
                        append("Shows the favorite translation results.\n\n")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(">Preferences\n")
                        }
                        append("Show the Application Settings, including selecting Translation Language.\n")
                    },
                    modifier = Modifier.padding(top = 12.dp).padding(horizontal = 18.dp),
                    style = TextStyle(color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Normal)
                )
            }

            DesktopScreenState.Recent -> {
                Text("RECENT")
            }

            DesktopScreenState.Favorite -> {
                Text("FAVORITE")
            }

            else -> {
                Text(
                    text = "Result",
                    modifier = Modifier.padding(top = 12.dp).padding(horizontal = 18.dp),
                    style = TextStyle(color = Color(0xFF3F8CFF), fontSize = 16.sp, fontWeight = FontWeight.Medium)
                )
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
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(12.dp)
                            .background(
                                color = Color(0x0F000000),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = translatedText,
                            modifier = Modifier.weight(1f),
                            style = TextStyle(color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Normal)
                        )
                        Spacer(modifier = Modifier.size(24.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "⌥↵ Save",
                                modifier = Modifier
                                    .background(
                                        color = Color(0x15000000),
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(6.dp),
                                color = Color.Black,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "↵ Copy",
                                modifier = Modifier
                                    .background(
                                        color = Color(0x15000000),
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(6.dp),
                                color = Color.Black,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(Unit) {
        viewModel.screenEvent.collect { screenEvent ->
            when (screenEvent) {
                is DesktopScreenEvent.CopyEvent -> {
                    onMinimize()
                    clipboardManager.setText(AnnotatedString(translatedText))
                    viewModel.setQuery("")
                }

                DesktopScreenEvent.ShowPreferences -> {
                    onShowPreferences()
                    viewModel.setQuery("")
                }
            }
        }
    }
}

@Composable
private fun makeSearchKeyword(query: String, vararg commands: String) {
    commands.firstOrNull() {
        query.contains(it.take(2))
    }?.let { command ->
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(Color.Black)) {
                    append(query)
                }
                append(command.removePrefix(query))
            },
            style = TextStyle(
                color = Color(0xFF999999),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        )
    } ?: Text(
        text = query,
        style = TextStyle(
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    )
}