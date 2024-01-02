package com.haeyum.shared.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.haeyum.shared.fontResources
import com.haeyum.shared.getPlatform
import com.haeyum.shared.presentation.Platform
import com.haeyum.transer.MR
import dev.icerock.moko.resources.compose.asFont
import dev.icerock.moko.resources.compose.fontFamilyResource

@Composable
fun PretendardFontFamily() = FontFamily(
    MR.fonts.Pretendard.thin.asFont(weight = FontWeight.Thin)!!,
    MR.fonts.Pretendard.extraLight.asFont(weight = FontWeight.ExtraLight)!!,
    MR.fonts.Pretendard.light.asFont(weight = FontWeight.Light)!!,
    MR.fonts.Pretendard.regular.asFont(weight = FontWeight.Normal)!!,
    MR.fonts.Pretendard.medium.asFont(weight = FontWeight.Medium)!!,
    MR.fonts.Pretendard.semiBold.asFont(weight = FontWeight.SemiBold)!!,
    MR.fonts.Pretendard.bold.asFont(weight = FontWeight.Bold)!!,
    MR.fonts.Pretendard.extraBold.asFont(weight = FontWeight.ExtraBold)!!,
    MR.fonts.Pretendard.black.asFont(weight = FontWeight.Black)!!,
)

@Composable
fun SuitFontFamily() = FontFamily(
    MR.fonts.SUIT.thin.asFont(weight = FontWeight.Thin)!!,
    MR.fonts.SUIT.extraLight.asFont(weight = FontWeight.ExtraLight)!!,
    MR.fonts.SUIT.light.asFont(weight = FontWeight.Light)!!,
    MR.fonts.SUIT.regular.asFont(weight = FontWeight.Normal)!!,
    MR.fonts.SUIT.medium.asFont(weight = FontWeight.Medium)!!,
    MR.fonts.SUIT.semiBold.asFont(weight = FontWeight.SemiBold)!!,
    MR.fonts.SUIT.bold.asFont(weight = FontWeight.Bold)!!,
    MR.fonts.SUIT.extraBold.asFont(weight = FontWeight.ExtraBold)!!,
)

@Composable
fun TranserTheme(content: @Composable () -> Unit) {
    MaterialTheme(typography = if(getPlatform() == Platform.iOS) SuitTypography() else TranserTypography(), content = content)
}