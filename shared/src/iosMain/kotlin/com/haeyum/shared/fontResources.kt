package com.haeyum.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font

@Composable
actual fun fontResources(font: String, weight: FontWeight) = Font(identity = font, data = byteArrayOf() , weight = weight)