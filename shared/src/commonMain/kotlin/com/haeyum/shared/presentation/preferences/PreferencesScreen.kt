@file:OptIn(ExperimentalComposeUiApi::class)

package com.haeyum.shared.presentation.preferences

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haeyum.shared.domain.model.translation.languages.Language
import com.haeyum.shared.getPlatform
import com.haeyum.shared.getVersion
import com.haeyum.shared.presentation.Platform
import com.haeyum.shared.presentation.theme.Black
import com.haeyum.shared.presentation.theme.ColorIcon
import com.haeyum.shared.presentation.theme.ColorMenuText
import com.haeyum.shared.presentation.theme.ColorSecondaryDivider

@Composable
fun PreferencesScreen(
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit,
    supportedLanguages: List<Language>,
    selectedSourceLanguage: String,
    selectedTargetLanguage: String,
    onSelectedSourceLanguage: (Language) -> Unit,
    onSelectedTargetLanguage: (Language) -> Unit,
    onClickClearData: () -> Unit,
    onClickContact: () -> Unit
) {
    var visibleSelectSourceLanguage by remember { mutableStateOf(false) }
    var visibleSelectTargetLanguage by remember { mutableStateOf(false) }

    var visibleSourceInfoAlert by remember { mutableStateOf(false) }
    var visibleTargetInfoAlert by remember { mutableStateOf(false) }

    var visibleClearDataAlert by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        header()

        Section(text = "Language") {
            Item(
                key = "Source Language",
                value = selectedSourceLanguage,
                iconVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Source Language",
                visibleInfo = true,
                onInfoClick = {
                    visibleSourceInfoAlert = true
                },
                onItemClick = {
                    visibleSelectSourceLanguage = true
                }
            )

            Item(
                key = "Target Language",
                value = selectedTargetLanguage,
                iconVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Target Language",
                visibleInfo = true,
                onInfoClick = {
                    visibleTargetInfoAlert = true
                },
                onItemClick = {
                    visibleSelectTargetLanguage = true
                }
            )
        }

        Section(text = "Information") {
            Item(key = "Clear Data", onItemClick = { visibleClearDataAlert = true })
            Item(key = "Contact", onItemClick = onClickContact)
            Item(key = "Version", value = getVersion())
        }
    }

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

    AlertDialog(
        visible = visibleClearDataAlert,
        onDismissRequest = { visibleClearDataAlert = false },
        onConfirmRequest = {
            visibleClearDataAlert = false
            onClickClearData()
        },
        confirmButtonText = "Clear",
        dismissButtonText = "Cancel",
        title = "Clear Data",
        text = "Are you sure you want to clear recent translates and saved translates data?"
    )

    VisibilitySelectLanguageScreen(
        title = "Source Language",
        visible = visibleSelectSourceLanguage,
        languages = supportedLanguages,
        onDismissRequest = {
            visibleSelectSourceLanguage = false
        },
        onSelectedLanguage = {
            onSelectedSourceLanguage(it)
            visibleSelectSourceLanguage = false
        }
    )

    VisibilitySelectLanguageScreen(
        title = "Target Language",
        visible = visibleSelectTargetLanguage,
        languages = supportedLanguages,
        onDismissRequest = {
            visibleSelectTargetLanguage = false
        },
        onSelectedLanguage = {
            onSelectedTargetLanguage(it)
            visibleSelectTargetLanguage = false
        }
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
private fun AlertDialog(
    visible: Boolean,
    onConfirmRequest: () -> Unit = {},
    onDismissRequest: () -> Unit,
    title: String,
    text: String,
    confirmButtonText: String = "OK",
    dismissButtonText: String? = null
) {
    if (visible)
        AlertDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = onConfirmRequest) {
                    Text(text = confirmButtonText)
                }
            },
            dismissButton = dismissButtonText?.let {
                {
                    TextButton(onClick = onDismissRequest) {
                        Text(text = it)
                    }
                }
            },
            modifier = Modifier.then(
                if (getPlatform() == Platform.Desktop) {
                    Modifier.fillMaxWidth(.8f)
                } else {
                    Modifier
                }
            ),
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
            color = Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Divider(modifier = Modifier.padding(top = 24.dp), color = ColorSecondaryDivider)
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
    visibleInfo: Boolean = false,
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
                Text(text = key, color = ColorMenuText, fontSize = 14.sp, fontWeight = FontWeight.Medium)

                if (visibleInfo) {
                    IconButton(onClick = onInfoClick) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "$key Information",
                            modifier = Modifier.size(20.dp),
                            tint = ColorIcon
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.height(32.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = value, color = ColorMenuText, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                if (iconVector != null) {
                    Icon(
                        imageVector = iconVector,
                        contentDescription = contentDescription,
                        modifier = Modifier.size(24.dp),
                        tint = ColorIcon
                    )
                }
            }
        }
    }
}