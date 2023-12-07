package com.haeyum.shared.presentation

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haeyum.shared.di.DIHelper
import com.haeyum.shared.presentation.component.Header
import com.haeyum.shared.presentation.theme.ColorHint
import com.haeyum.shared.presentation.theme.ColorSecondaryDivider
import com.haeyum.shared.presentation.theme.ColorText
import com.haeyum.shared.presentation.theme.White
import com.haeyum.shared.presentation.vector.ContentCopy

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun iOSTranslateScreen(modifier: Modifier = Modifier, onShowSnackbar: (String) -> Unit) {
    val viewModel by produceState(initialValue = DIHelper().translateViewModel) {
        awaitDispose {
            value.onCleared()
        }
    }
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    val clipboardManager = LocalClipboardManager.current
    val focusManager = LocalFocusManager.current

    val scrollState = rememberScrollState()

    val text by viewModel.text.collectAsState()
    val translatedText by viewModel.translatedText.collectAsState()

    val colorWhite = White
    val colorSecondaryDivider = ColorSecondaryDivider

    val backgroundColor = remember(isSystemInDarkTheme(), scrollState.canScrollBackward, scrollState.canScrollForward) {
        if (scrollState.maxValue == 0) {
            colorWhite
        } else if (!scrollState.canScrollBackward) {
            colorSecondaryDivider
        } else if (!scrollState.canScrollForward) {
            Color(0xFF79AFFF)
        } else {
            colorWhite
        }
    }

    val isExistsSavedTranslate by viewModel.isExistsSavedTranslate.collectAsState()

    Column(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures(
                onTap = {
                    focusManager.clearFocus()
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
                .background(color = backgroundColor)
                .verticalScroll(state = scrollState)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = ColorSecondaryDivider)
                    .padding(bottom = 32.dp)
            ) {
                if (text.isNotEmpty()) {
                    IconButton(
                        onClick = viewModel::clearText,
                        modifier = Modifier.align(alignment = Alignment.TopEnd).padding(4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Clear text")
                    }
                }

                BasicTextField(
                    value = text,
                    onValueChange = viewModel::setText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(top = 48.dp),
                    textStyle = TextStyle(
                        color = ColorText,
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
            }

            AnimatedVisibility(
                visible = translatedText.isNotEmpty(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color(0xFF79AFFF))
                        .padding(start = 24.dp, end = 8.dp, top = 32.dp, bottom = 12.dp)
                ) {
                    Text(
                        text = translatedText,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 20.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 18.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            ActionButton(
                                imageVector = Icons.Filled.ContentCopy,
                                contentDescription = "Copy",
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(translatedText))
                                    onShowSnackbar("Copied to clipboard.")
                                }
                            )

                            ActionButton(
                                imageVector = if (isExistsSavedTranslate) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = if (isExistsSavedTranslate) "Delete Saved" else "Save",
                                onClick = viewModel::toggleSave
                            )
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.snackbarEvent.collect {
            onShowSnackbar(it)
        }
    }
}

@Composable
private fun ActionButton(
    imageVector: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = Color.Black
        )
    }
}