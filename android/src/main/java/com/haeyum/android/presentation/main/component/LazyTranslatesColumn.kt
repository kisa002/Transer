package com.haeyum.android.presentation.main.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haeyum.common.presentation.theme.Black
import com.haeyum.common.presentation.theme.ColorDivider
import com.haeyum.common.presentation.theme.ColorText

@Composable
fun LazyTranslatesColumn(translates: List<Pair<String, String>>, onClick: (AnnotatedString) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(translates) { index, (translatedText, originalText) ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick(AnnotatedString(translatedText))
                    }
                    .padding(horizontal = 12.dp, vertical = 12.dp)
            ) {
                Text(text = translatedText, color = Black, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(
                    text = originalText,
                    color = ColorText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Thin
                )
            }

            if (index != translates.size - 1) {
                Divider(color = ColorDivider, thickness = 1.dp)
            }
        }
    }
}