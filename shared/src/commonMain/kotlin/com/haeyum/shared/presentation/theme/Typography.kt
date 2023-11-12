package com.haeyum.shared.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TranserTypography() = Typography(
    body1 = TextStyle(
        fontFamily = PretendardFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)