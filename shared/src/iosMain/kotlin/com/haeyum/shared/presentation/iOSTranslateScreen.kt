package com.haeyum.shared.presentation

import TranslateViewModel
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(state = rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = ColorSecondaryDivider)
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = viewModel::setText,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 36.dp),
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
                        onClick = viewModel::clearText,
                        modifier = Modifier.align(alignment = Alignment.TopEnd).padding(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Clear text")
                    }
                }
            }

            AnimatedVisibility(
                visible = translatedText.isNotEmpty(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color(0xFF79AFFF))
                        .padding(horizontal = 24.dp, vertical = 36.dp)
                ) {
                    Text(
                        text = translatedText,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}