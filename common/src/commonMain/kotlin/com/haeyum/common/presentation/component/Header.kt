package com.haeyum.common.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haeyum.common.presentation.theme.*

@Composable
fun Header(title: String, imageVector: ImageVector? = null, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(8.dp))
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(color = ColorSecondaryBackground, shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .padding(8.dp)
        ) {
            Text(
                text = title,
                modifier = Modifier.align(Alignment.Center),
                color = Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            if (imageVector != null)
                IconButton(onClick = onClick, modifier = Modifier.align(Alignment.CenterEnd)) {
                    Icon(
                        imageVector = imageVector,
                        contentDescription = "Close",
                        tint = ColorIcon
                    )
                }
        }

        Divider(color = ColorSecondaryDivider)
        Spacer(modifier = Modifier.height(16.dp))
    }
}