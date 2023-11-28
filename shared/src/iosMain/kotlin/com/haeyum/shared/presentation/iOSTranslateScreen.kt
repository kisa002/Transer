package com.haeyum.shared.presentation

import TranslateViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
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
import com.haeyum.shared.presentation.component.Header
import com.haeyum.shared.presentation.theme.ColorHint
import com.haeyum.shared.presentation.theme.ColorSecondaryDivider

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun iOSTranslateScreen(modifier: Modifier = Modifier, viewModel: TranslateViewModel) {
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    val text by viewModel.text.collectAsState()
    val translatedText by viewModel.translatedText.collectAsState()

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
                    onValueChange = viewModel::setText,
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
                        onClick = { viewModel.setText("") },
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