package com.haeyum.shared.presentation.theme

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
    get() = if (isSystemInDarkTheme()) Color(0xFF1B1B1B) else Color(0xFFEFEFEF) // 0xFF252525

val ColorDivider
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF4A4A4A) else Color(0xFFAFAFAF)

val ColorHint = Color(0xFF999999) // Hint Text

val ColorLightBlue = Color(0xFF3F8CFF)

val ColorError
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFE62E2E) else Color(0xFFE60000)

val ColorSelected
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0x0FABABAB) else Color(0x0F000000)

val ColorSelectedAction
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0x15ABABAB) else Color(0x15000000)

val ColorIcon
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF323232) else Color(0xFFADADAD)

val ColorSecondaryDivider
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF323232) else Color(0xFFE5E5E5)
val ColorSecondaryBackground
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF1D1D1D) else Color(0xFFFBFBFB)

val ColorText
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFCACACA) else Color.Black

val ColorMenuText
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFCACACA) else Color(0xFF333333)