package com.haeyum.shared.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.haeyum.transer.MR
import dev.icerock.moko.resources.compose.asFont

@Composable
fun TranserTypography() = Typography(defaultFontFamily = PretendardFontFamily())

@Composable
fun SuitTypography() = Typography(defaultFontFamily = SuitFontFamily())