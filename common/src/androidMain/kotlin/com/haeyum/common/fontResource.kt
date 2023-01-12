package com.haeyum.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight

@Composable
actual fun fontResources(font: String, weight: FontWeight): Font {
    val context = LocalContext.current
    val name = font.substringBefore(".")
    val fontRes = context.resources.getIdentifier(name, "font", context.packageName)

    return Font(fontRes, weight)
}