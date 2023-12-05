@file:OptIn(ExperimentalMaterialApi::class)

package com.haeyum.transer.presentation.translation

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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.unit.dp
import com.haeyum.transer.presentation.translation.section.TranslateFailureSection
import com.haeyum.transer.presentation.translation.section.TranslateSuccessSection
import com.haeyum.shared.presentation.component.RainbowCircularProgressIndicator
import com.haeyum.shared.presentation.theme.ColorIcon
import com.haeyum.shared.presentation.theme.Transparent
import com.haeyum.shared.presentation.theme.White
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
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

                                TranslateSuccessSection(
                                    screenState.originalText,
                                    screenState.translatedText,
                                    isExistsSavedTranslate = isExistsSavedTranslate,
                                    onRequestOpen = onRequestOpen,
                                    onRequestCopy = {
                                        clipboardManager.setText(it)
                                    },
                                    onRequestToggleSave = viewModel::toggleSave
                                )
                            }

                            TranslationScreenState.DisconnectedNetwork -> {
                                TranslateFailureSection(
                                    title = "Disconnected Network",
                                    description = "Please check your network connection."
                                )
                            }

                            TranslationScreenState.FailedTranslate -> {
                                TranslateFailureSection(
                                    title = "Failed Translate",
                                    description = "Please change the text or try again later."
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

        LaunchedEffect(Unit) {
            snapshotFlow { bottomSheetState.currentValue }
                .filter { it == ModalBottomSheetValue.Hidden }
                .collectLatest {
                    onRequestFinish()
                }
        }
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