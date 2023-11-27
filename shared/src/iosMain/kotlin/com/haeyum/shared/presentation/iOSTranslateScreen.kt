@file:OptIn(ExperimentalComposeUiApi::class)

package com.haeyum.shared.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haeyum.shared.di.DIHelper
import com.haeyum.shared.presentation.component.Header
import com.haeyum.shared.presentation.theme.ColorHint
import com.haeyum.shared.presentation.theme.ColorSecondaryDivider
import kotlinx.coroutines.delay

@Composable
fun iOSTranslateScreen(modifier: Modifier = Modifier) {
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    val translateUseCase = rememberSaveable { DIHelper().translateUseCase }
    val addRecentTranslateUseCase = rememberSaveable { DIHelper().addRecentTranslateUseCase }

    val (text, setText) = rememberSaveable("A") { mutableStateOf("") }
    val translatedText by produceState(initialValue = "", key1 = text) {
        if (text.isNotEmpty()) {
            delay(700)
            val texts = text to translateUseCase(text).translatedText
            value = texts.second

            addRecentTranslateUseCase(originalText = texts.first, translatedText = texts.second)
        } else {
            value = ""
        }
    }

    Column(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures(
                onTap = {
                    softwareKeyboardController?.hide()
                }
            )
        }
    ) {
        Header(
            title = "Translate",
        )

        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(.5f)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(color = ColorSecondaryDivider)
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = setText,
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        textAlign = TextAlign.Start
                    ),
                    decorationBox = { innerTextField ->
                        Text(
                            text = if (text.isEmpty()) "Enter text to translate..." else "",
                            modifier = Modifier.fillMaxWidth(),
                            color = ColorHint,
                            fontSize = 20.sp,
                        )
                        innerTextField()
                    }
                )
                if (text.isNotEmpty()) {
                    IconButton(
                        onClick = { setText("") },
                        modifier = Modifier.align(alignment = Alignment.TopEnd).padding(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Clear text")
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(color = Color(0xFF79AFFF))
                    .padding(24.dp)
            ) {
                Text(
                    text = translatedText,
                    modifier = Modifier.fillMaxSize(),
                    fontSize = 20.sp
                )
            }
        }
    }
}