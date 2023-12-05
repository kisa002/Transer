package com.haeyum.transer.presentation.translation.section

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haeyum.shared.presentation.theme.Black
import com.haeyum.shared.presentation.theme.ColorError

@Composable
fun TranslateFailureSection(title: String, description: String) {
    Text(
        text = title,
        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
        color = ColorError,
        fontSize = 20.sp
    )
    Text(
        text = description,
        color = Black,
        fontSize = 16.sp
    )
}