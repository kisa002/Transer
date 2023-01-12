package com.haeyum.android.presentation.translation

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.haeyum.android.presentation.main.MainActivity
import com.haeyum.common.presentation.component.RainbowCircularProgressIndicator
import com.haeyum.common.presentation.theme.*
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalMaterialApi::class)
class TranslationActivity : AppCompatActivity() {
    private val viewModel by viewModel<TranslationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setBackgroundDrawable(ColorDrawable(0))
        window.statusBarColor = 0x00000000
        WindowCompat.setDecorFitsSystemWindows(window, false)

        viewModel.requestTranslation(intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString())

        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = ::finish
                    ),
                contentAlignment = Alignment.Center
            ) {
                val screenState by viewModel.screenState.collectAsState()
                val isExistsSavedTranslate by viewModel.isExistsSavedTranslate.collectAsState()

                val coroutineScope = rememberCoroutineScope()
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
                                            IconButton(
                                                onClick = {
                                                    startActivity(
                                                        Intent(
                                                            this@TranslationActivity,
                                                            MainActivity::class.java
                                                        ).apply {
                                                            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                                        }
                                                    )
                                                },
                                                modifier = Modifier.offset(x = (-12).dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Settings,
                                                    contentDescription = "Preferences",
                                                    tint = ColorText
                                                )
                                            }

                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                IconButton(
                                                    onClick = {
                                                        clipboardManager.setText(AnnotatedString(screenState.translatedText))
                                                    }
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.ContentCopy,
                                                        contentDescription = "Copy",
                                                        tint = ColorText
                                                    )
                                                }

                                                IconButton(onClick = viewModel::toggleSave) {
                                                    Icon(
                                                        imageVector = if (isExistsSavedTranslate) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                                        contentDescription = if (isExistsSavedTranslate) "Delete Saved" else "Save",
                                                        tint = ColorText
                                                    )
                                                }
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
//                    sheetElevation = 0.dp,
                    sheetBackgroundColor = Transparent,
                    sheetContentColor = Transparent,
//                    scrimColor = Transparent,
                    content = {

                    }
                )
                LaunchedEffect(bottomSheetState) {
                    if (bottomSheetState.currentValue == ModalBottomSheetValue.Hidden)
                        finish()
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
}