package com.haeyum.android.presentation.translation.section

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haeyum.common.presentation.theme.Black
import com.haeyum.common.presentation.theme.ColorIcon
import com.haeyum.common.presentation.theme.ColorLightBlue
import com.haeyum.common.presentation.theme.ColorText

@Composable
fun TranslateSuccessSection(
    originalText: String,
    translatedText: String,
    isExistsSavedTranslate: Boolean,
    onRequestOpen: () -> Unit,
    onRequestCopy: (AnnotatedString) -> Unit,
    onRequestToggleSave: () -> Unit
) {
    Text(
        text = "Original Text",
        color = ColorLightBlue,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
    Text(
        text = originalText,
        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
        color = Black,
        fontSize = 18.sp
    )

    Divider(
        modifier = Modifier.padding(vertical = 12.dp).fillMaxWidth(),
        color = ColorIcon,
        thickness = 1.dp
    )

    Text(
        text = "Translated Text",
        color = ColorLightBlue,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
    Text(
        text = translatedText,
        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
        color = Black,
        fontSize = 18.sp
    )

    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionButton(
            imageVector = Icons.Default.ArrowCircleRight,
            contentDescription = "Open App",
            modifier = Modifier.offset(x = (-12).dp),
            onClick = onRequestOpen
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            ActionButton(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = "Copy",
                onClick = {
                    onRequestCopy(AnnotatedString(translatedText))
                }
            )

            ActionButton(
                imageVector = if (isExistsSavedTranslate) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = if (isExistsSavedTranslate) "Delete Saved" else "Save",
                onClick = onRequestToggleSave
            )
        }
    }
}

@Composable
private fun ActionButton(
    imageVector: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = ColorText
        )
    }
}