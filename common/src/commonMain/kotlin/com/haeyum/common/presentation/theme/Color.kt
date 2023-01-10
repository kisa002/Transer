package com.haeyum.common.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Black
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFE2E2E2) else Color.Black

val White
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF1B1B1B) else Color.White

val Transparent = Color.Transparent

val ColorsLoading = listOf(
    Color(0xFF5851D8),
    Color(0xFF833AB4),
    Color(0xFFC13584),
    Color(0xFFE1306C),
    Color(0xFFFD1D1D),
    Color(0xFFF56040),
    Color(0xFFF77737),
    Color(0xFFFCAF45),
    Color(0xFFFFDC80),
    Color(0xFF5851D8)
)

val ColorBackground
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF252525) else Color(0xFFEFEFEF) // Main Background

val ColorDivider
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF4A4A4A) else Color(0xFFAFAFAF) // Divider

val ColorHint = Color(0xFF999999) // Hint Text

val ColorLightBlue = Color(0xFF3F8CFF)

val ColorError
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFE62E2E) else Color(0xFFE60000)

val ColorSelected
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0x0FABABAB) else Color(0x0F000000) // Selected Translated Item

val ColorSelectedAction
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0x15ABABAB) else Color(0x15000000)// Selected Translated Item Action