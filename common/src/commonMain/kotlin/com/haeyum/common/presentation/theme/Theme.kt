package com.haeyum.common.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font


val PretendardFontFamily = FontFamily(
    Font("font/pretendard_thin.otf", FontWeight.Thin),
    Font("font/pretendard_extra_light.otf", FontWeight.ExtraLight),
    Font("font/pretendard_light.otf", FontWeight.Light),
    Font("font/pretendard_regular.otf", FontWeight.Normal),
    Font("font/pretendard_medium.otf", FontWeight.Medium),
    Font("font/pretendard_semi_bold.otf", FontWeight.SemiBold),
    Font("font/pretendard_bold.otf", FontWeight.Bold),
)

@Composable
fun TranserTheme(content: @Composable () -> Unit) {
    MaterialTheme(typography = Typography, content = content)
}