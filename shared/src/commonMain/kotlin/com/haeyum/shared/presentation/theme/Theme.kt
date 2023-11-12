package com.haeyum.shared.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.haeyum.shared.fontResources

@Composable
fun PretendardFontFamily() = FontFamily(
    fontResources("font/pretendard_extra_light.otf", FontWeight.Thin),
    fontResources("font/pretendard_extra_light.otf", FontWeight.ExtraLight),
    fontResources("font/pretendard_light.otf", FontWeight.Light),
    fontResources("font/pretendard_regular.otf", FontWeight.Normal),
    fontResources("font/pretendard_medium.otf", FontWeight.Medium),
    fontResources("font/pretendard_semi_bold.otf", FontWeight.SemiBold),
    fontResources("font/pretendard_bold.otf", FontWeight.Bold),
)

@Composable
fun TranserTheme(content: @Composable () -> Unit) {
    MaterialTheme(typography = TranserTypography(), content = content)
}