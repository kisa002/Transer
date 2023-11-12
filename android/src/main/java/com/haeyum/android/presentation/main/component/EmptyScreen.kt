package com.haeyum.android.presentation.main.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BorderAll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haeyum.shared.presentation.theme.Black

@Composable
fun EmptyScreen(text: String) {
    Column(
        modifier = Modifier.fillMaxSize(1f).padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.BorderAll,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = Black
        )
        Text(text = text, color = Black)
    }
}