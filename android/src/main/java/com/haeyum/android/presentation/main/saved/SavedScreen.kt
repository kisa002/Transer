package com.haeyum.android.presentation.main.saved

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haeyum.common.presentation.theme.Black
import com.haeyum.common.presentation.theme.ColorDivider
import com.haeyum.common.presentation.theme.ColorText
import org.koin.androidx.compose.koinViewModel

@Composable
fun SavedScreen(modifier: Modifier, viewModel: SavedViewModel = koinViewModel()) {
    val savedTranslates by viewModel.savedTranslates.collectAsState()
    val clipboardManager = LocalClipboardManager.current

    Column(modifier = modifier) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 6.dp)) {
            itemsIndexed(savedTranslates) { index, savedTranslate ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            clipboardManager.setText(AnnotatedString(savedTranslate.translatedText))
                        }
                        .padding(horizontal = 12.dp, vertical = 12.dp)
                ) {
                    Text(text = savedTranslate.translatedText, color = Black, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Text(
                        text = savedTranslate.originalText,
                        color = ColorText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Thin
                    )
                }

                if (index != savedTranslates.size - 1) {
                    Divider(color = ColorDivider, thickness = 1.dp)
                }
            }
        }
    }
}