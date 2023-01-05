package com.haeyum.common.presentation.preferences

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    selectedNativeLanguage: String,
    selectedTargetLanguage: String,
    onCloseRequest: () -> Unit,
    onSelectedNativeLanguage: (Language) -> Unit,
    onSelectedTargetLanguage: (Language) -> Unit
) {
    var isShowSelectNativeLanguage by remember { mutableStateOf(false) }
    var isShowSelectTargetLanguage by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = Color(0xFFE5E5E5), shape = RoundedCornerShape(8.dp))
    ) {
        Header(title = "Preferences", imageVector = Icons.Default.Close, onClick = onCloseRequest)

        Section(text = "Language") {
            Item(
                key = "Native Language",
                value = selectedNativeLanguage,
                iconVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Native Language"
            ) {
                isShowSelectNativeLanguage = true
            }

            Item(
                key = "Target Language",
                value = selectedTargetLanguage,
                iconVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Target Language"
            ) {
                isShowSelectTargetLanguage = true
            }
        }

        Section(text = "Information") {
            Item(key = "Contact")
            Item(key = "Version", value = "1.0.0")
        }
    }

    AnimatedVisibility(
        visible = isShowSelectNativeLanguage,
        enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
    ) {
        SelectLanguageScreen(
            title = "Native Language",
            languages = supportedLanguages,
            onDismissRequest = {
                isShowSelectNativeLanguage = false
            },
            onSelectedLanguage = {
                onSelectedNativeLanguage(it)
                isShowSelectNativeLanguage = false
            },
        )
    }

    AnimatedVisibility(
        visible = isShowSelectTargetLanguage,
        enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
    ) {
        SelectLanguageScreen(
            title = "Target Language",
            languages = supportedLanguages,
            onDismissRequest = {
                isShowSelectTargetLanguage = false
            },
            onSelectedLanguage = {
                onSelectedTargetLanguage(it)
                isShowSelectTargetLanguage = false
            },
        )
    }
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
    onClick: () -> Unit = {}
) {
    TextButton(
        onClick = onClick, contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = key, color = Color(0xFF333333), fontSize = 14.sp, fontWeight = FontWeight.Medium
            )
            Row(
                modifier = Modifier.height(32.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value, color = Color(0xFF333333), fontSize = 14.sp, fontWeight = FontWeight.Medium
                )
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