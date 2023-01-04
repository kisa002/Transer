package com.haeyum.common.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PreferencesScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = Color(0xFFE5E5E5), shape = RoundedCornerShape(8.dp))
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(color = Color(0xFFFBFBFB), shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "Preferences",
                modifier = Modifier.align(Alignment.Center),
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = {}, modifier = Modifier.align(Alignment.CenterEnd)) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color(0xFFADADAD))
            }
        }
        Divider(color = Color(0xFFE5E5E5))
        Spacer(modifier = Modifier.height(16.dp))

        Section(text = "Language") {
            Item(
                key = "Native Language",
                value = "Korean",
                iconVector = Icons.Default.ArrowDropDown,
                contentDescription = "Native Language"
            ) {

            }
            Item(
                key = "Target Language",
                value = "English",
                iconVector = Icons.Default.ArrowDropDown,
                contentDescription = "Target Language"
            ) {

            }
        }

        Section(text = "Information") {
            Item(
                key = "Contact",
            )
            Item(key = "Version", value = "1.0.0")
        }
    }
}

@Composable
private fun Section(text: String, content: @Composable () -> Unit) {
    Column {
        Text(
            text = text,
            modifier = Modifier.padding(top = 24.dp).padding(horizontal = 16.dp),
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Divider(modifier = Modifier.padding(top = 24.dp), color = Color(0xFFE5E5E5))
        Spacer(modifier = Modifier.height(12.dp))
        content()
    }
}

@Composable
private fun Item(
    key: String,
    value: String = "",
    iconVector: ImageVector? = null,
    contentDescription: String? = null,
    onClick: () -> Unit = {}
) {
    TextButton(
        onClick = onClick,
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = key,
                color = Color(0xFF333333),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Row(
                modifier = Modifier.height(32.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    color = Color(0xFF333333),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                if (iconVector != null) {
                    IconButton(onClick = onClick, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = iconVector,
                            contentDescription = contentDescription,
                            tint = Color(0xFFADADAD)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PreferencesPreviewScreen() {
    MaterialTheme {
        PreferencesScreen()
    }
}