package com.haeyum.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
    val platformName = getPlatformName()

    val (inputText, setInputText) = remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }

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
                value = inputText,
                onValueChange = setInputText,
                modifier = Modifier.weight(1f).focusRequester(focusRequester),
                textStyle = TextStyle(color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Medium),
                decorationBox = { innerTextField ->
                    if (inputText.isEmpty()) {
                        Text(
                            text = "Enter text to translate...",
                            style = TextStyle(
                                color = Color(0xFF999999),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                    innerTextField()
                }
            )
        }
        Divider(modifier = Modifier, color = Color(0xFFAFAFAF), thickness = (0.5).dp)
        Text(
            text = "Guide",
            modifier = Modifier.padding(top = 12.dp).padding(horizontal = 18.dp),
            style = TextStyle(color = Color(0xFF3F8CFF), fontSize = 16.sp, fontWeight = FontWeight.Medium)
        )
        Text(
            text = buildAnnotatedString {
                append("Commands can use through")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline)) {
                    append(" >\n")
                }
                append("If you want to show or hide this guide, you can enter ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline)) {
                    append(">guide")
                }
                append(" to toggle it on/off.\n\n")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(">Recent\n")
                }
                append("Shows recent translation results.\n\n")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(">Saved\n")
                }
                append("Shows the saved translation results.\n\n")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(">Preferences\n")
                }
                append("Show the Application Settings, including selecting Translation Language.\n")
            },
            modifier = Modifier.padding(top = 12.dp).padding(horizontal = 18.dp),
            style = TextStyle(color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Normal)
        )
        Text(
            text = "You typed: $inputText",
            modifier = Modifier.padding(top = 12.dp).padding(horizontal = 18.dp),
            style = TextStyle(color = Color(0xFF3F8CFF), fontSize = 14.sp, fontWeight = FontWeight.Medium)
        )
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
