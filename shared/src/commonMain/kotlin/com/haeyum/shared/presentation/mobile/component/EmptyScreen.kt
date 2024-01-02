package com.haeyum.shared.presentation.mobile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haeyum.shared.presentation.theme.Black
import com.haeyum.shared.presentation.vector.BorderAll

@Composable
fun EmptyScreen(text: String) {
    Column(
        modifier = Modifier.fillMaxSize(1f).padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.BorderAll,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = Black
        )
        Text(text = text, color = Black)
    }
}