package com.haeyum.common.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val ColorPrimary = Color(0xFF6200EE)

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

val Color3F8CFF = Color(0xFF3F8CFF) // Blue

val ColorE60000
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFE62E2E) else Color(0xFFE60000) // Error

val ColorEFEFEF
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF252525) else Color(0xFFEFEFEF) // Main Background

val ColorAFAFAF
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF4A4A4A) else Color(0xFFAFAFAF) // Divider

val Color999999 = Color(0xFF999999) // Hint Text

val Color0F000000
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0x0FABABAB) else Color(0x0F000000) // Selected Translated Item

val Color15000000
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0x15ABABAB) else Color(0x15000000)// Selected Translated Item Action

//val ColorEFEFEF = Color(0xFFEFEFEF) // main?
//val ColorAFAFAF = Color(0xFFAFAFAF) // divider
//val Color999999 = Color(0xFF999999) // text
//val ColorE60000 = Color(0xFFE60000) // error
//val Color3F8CFF = Color(0xFF3F8CFF) // link
//val ColorFBFBFB = Color(0xFFFBFBFB) // background
//val ColorADADAD = Color(0xFFADADAD) // icon
//val ColorE5E5E5 = Color(0xFFE5E5E5) // divider header
//val Color0F000000 = Color(0x0F000000) // shadow - translated item
//val Color15000000 = Color(0x15000000) // shadow - delete