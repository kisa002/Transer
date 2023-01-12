@file:OptIn(ExperimentalMaterialApi::class)

package com.haeyum.android.presentation.translation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haeyum.common.presentation.component.RainbowCircularProgressIndicator
import com.haeyum.common.presentation.theme.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun TranslationScreen(
    onRequestOpen: () -> Unit,
    onRequestFinish: () -> Unit,
    viewModel: TranslationViewModel = koinViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onRequestFinish
            ),
        contentAlignment = Alignment.Center
    ) {
        val screenState by viewModel.screenState.collectAsState()
        val isExistsSavedTranslate by viewModel.isExistsSavedTranslate.collectAsState()

        val bottomSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.HalfExpanded,
            skipHalfExpanded = false
        )

        val clipboardManager = LocalClipboardManager.current

        ModalBottomSheetLayout(
            sheetContent = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .background(color = White, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .padding(horizontal = 24.dp)
                        .padding(top = 14.dp, bottom = 24.dp)
                ) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth(.16f)
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally),
                        color = ColorIcon,
                        thickness = 4.dp
                    )

                    AnimatedVisibility(screenState == TranslationScreenState.Translating) {
                        TranslatingProgressIndicator()
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 18.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        when (screenState) {
                            TranslationScreenState.Translating -> {
                                /*no-op*/
                            }

                            is TranslationScreenState.Translated -> {
                                val screenState = (screenState as TranslationScreenState.Translated)

                                Text(
                                    text = "Original Text",
                                    color = ColorLightBlue,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = screenState.originalText,
                                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                                    color = Black,
                                    fontSize = 18.sp
                                )

                                Divider(
                                    modifier = Modifier.padding(vertical = 12.dp).fillMaxWidth(),
                                    color = ColorIcon,
                                    thickness = 1.dp
                                )

                                Text(
                                    text = "Translated Text",
                                    color = ColorLightBlue,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = screenState.translatedText,
                                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                                    color = Black,
                                    fontSize = 18.sp
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(top = 18.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    ActionButton(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = "Preferences",
                                        modifier = Modifier.offset(x = (-12).dp),
                                        onClick = onRequestOpen
                                    )

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        ActionButton(
                                            imageVector = Icons.Default.ContentCopy,
                                            contentDescription = "Copy",
                                            onClick = {
                                                clipboardManager.setText(AnnotatedString(screenState.translatedText))
                                            }
                                        )

                                        ActionButton(
                                            imageVector = if (isExistsSavedTranslate) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                            contentDescription = if (isExistsSavedTranslate) "Delete Saved" else "Save",
                                            onClick = viewModel::toggleSave
                                        )
                                    }
                                }
                            }

                            TranslationScreenState.DisconnectedNetwork -> {
                                Text(text = "Disconnected Network", color = Black, fontSize = 16.sp)
                                Text(
                                    text = "Please check your network connection.",
                                    color = Black,
                                    fontSize = 16.sp
                                )
                            }

                            TranslationScreenState.FailedTranslate -> {
                                Text(text = "Failed Translate", color = Black, fontSize = 16.sp)
                                Text(
                                    text = "Please change the text or try again later.",
                                    color = Black,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxSize(),
            sheetState = bottomSheetState,
            sheetBackgroundColor = Transparent,
            sheetContentColor = Transparent,
            content = { }
        )
        LaunchedEffect(bottomSheetState) {
            if (bottomSheetState.currentValue == ModalBottomSheetValue.Hidden)
                onRequestFinish()
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
            tint = ColorText
        )
    }
}

@Composable
private fun TranslatingProgressIndicator() {
    Box(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(.46f),
        contentAlignment = Alignment.Center
    ) {
        RainbowCircularProgressIndicator(
            modifier = Modifier.size(96.dp),
            strokeWidth = 6.dp
        )
    }
}