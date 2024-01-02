package com.haeyum.shared.presentation.mobile

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun RowScope.MainBottomNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    imageVector: ImageVector,
    text: String
) {
    BottomNavigationItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = if (selected) Color.White else Color.DarkGray
            )
        },
        label = {
            Text(text = text, color = if (selected) Color.White else Color.DarkGray)
        }
    )
}