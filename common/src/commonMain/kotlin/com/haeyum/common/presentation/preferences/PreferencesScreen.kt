@file:OptIn(ExperimentalMaterialApi::class)

package com.haeyum.common.presentation.preferences

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haeyum.common.domain.model.translation.languages.Language
import com.haeyum.common.presentation.component.Header

@Composable
fun PreferencesScreen(
    supportedLanguages: List<Language>,
    selectedSourceLanguage: String,
    selectedTargetLanguage: String,
    onCloseRequest: () -> Unit,
    onSelectedSourceLanguage: (Language) -> Unit,
    onSelectedTargetLanguage: (Language) -> Unit,
    onClickContact: () -> Unit
) {
    var isVisibleSelectSourceLanguage by remember { mutableStateOf(false) }
    var isVisibleSelectTargetLanguage by remember { mutableStateOf(false) }

    var visibleSourceInfoAlert by remember { mutableStateOf(false) }
    var visibleTargetInfoAlert by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
            .background(color = Color.White)
            .border(width = 1.dp, color = Color(0xFFE5E5E5), shape = RoundedCornerShape(8.dp))
    ) {
        Header(title = "Preferences", imageVector = Icons.Default.Close, onClick = onCloseRequest)

        Section(text = "Language") {
            Item(
                key = "Source Language",
                value = selectedSourceLanguage,
                iconVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Source Language",
                visibileInfo = true,
                onInfoClick = {
                    visibleSourceInfoAlert = true
                },
                onItemClick = {
                    isVisibleSelectSourceLanguage = true
                }
            )

            Item(
                key = "Target Language",
                value = selectedTargetLanguage,
                iconVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Target Language",
                visibileInfo = true,
                onInfoClick = {
                    visibleTargetInfoAlert = true
                },
                onItemClick = {
                    isVisibleSelectTargetLanguage = true
                }
            )
        }

        Section(text = "Information") {
            Item(key = "Contact", onItemClick = onClickContact)
            Item(key = "Version", value = "1.0.0")
        }
    }

    VisibilitySelectLanguageScreen(
        title = "Source Language",
        visible = isVisibleSelectSourceLanguage,
        languages = supportedLanguages,
        onDismissRequest = {
            isVisibleSelectSourceLanguage = false
        },
        onSelectedLanguage = {
            onSelectedSourceLanguage(it)
            isVisibleSelectSourceLanguage = false
        }
    )

    VisibilitySelectLanguageScreen(
        title = "Target Language",
        visible = isVisibleSelectTargetLanguage,
        languages = supportedLanguages,
        onDismissRequest = {
            isVisibleSelectTargetLanguage = false
        },
        onSelectedLanguage = {
            onSelectedTargetLanguage(it)
            isVisibleSelectTargetLanguage = false
        }
    )

    // If the language you enter is a language other than the target language, it will be translated into the source language.
    AlertDialog(
        visible = visibleSourceInfoAlert,
        onDismissRequest = { visibleSourceInfoAlert = false },
        title = "What is Source Language?",
        text = "The source language is basically the language of the text to be used for translation.",
    )

    AlertDialog(
        visible = visibleTargetInfoAlert,
        onDismissRequest = { visibleTargetInfoAlert = false },
        title = "What is Target Language?",
        text = "The target language is the language in which you want to translate the text you enter.",
    )
}

@Composable
fun VisibilitySelectLanguageScreen(
    title: String,
    visible: Boolean,
    languages: List<Language>,
    onDismissRequest: () -> Unit,
    onSelectedLanguage: (Language) -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
    ) {
        SelectLanguageScreen(
            title = title,
            languages = languages,
            onDismissRequest = onDismissRequest,
            onSelectedLanguage = onSelectedLanguage,
        )
    }
}

@Composable
private fun AlertDialog(visible: Boolean, onDismissRequest: () -> Unit, title: String, text: String) {
    if (visible)
        AlertDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "OK")
                }
            },
            modifier = Modifier.fillMaxWidth(.8f),
            title = {
                Text(text = title)
            },
            text = {
                Text(text = text)
            }
        )
}

@Composable
private fun Section(text: String, content: @Composable () -> Unit) {
    Column {
        Text(
            text = text,
            modifier = Modifier.padding(top = 24.dp).padding(horizontal = 16.dp),
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Divider(modifier = Modifier.padding(top = 24.dp), color = Color(0xFFE5E5E5))
        Spacer(modifier = Modifier.height(12.dp))
        content()
    }
}

@Composable
private fun Item(
    key: String,
    value: String = "",
    iconVector: ImageVector? = null,
    contentDescription: String? = null,
    visibileInfo: Boolean = false,
    onInfoClick: () -> Unit = {},
    onItemClick: () -> Unit = {},
) {
    TextButton(
        onClick = onItemClick, contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = key, color = Color(0xFF333333), fontSize = 14.sp, fontWeight = FontWeight.Medium)

                if (visibileInfo) {
                    IconButton(onClick = onInfoClick) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "$key Information",
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFFADADAD)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.height(32.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = value, color = Color(0xFF333333), fontSize = 14.sp, fontWeight = FontWeight.Medium)
                if (iconVector != null) {
                    Icon(
                        imageVector = iconVector,
                        contentDescription = contentDescription,
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFFADADAD)
                    )
                }
            }
        }
    }
}