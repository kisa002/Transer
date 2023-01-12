package com.haeyum.android.presentation.main.recent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
fun RecentTranslateScreen(modifier: Modifier, viewModel: RecentTranslateViewModel = koinViewModel()) {
    val recentTranslates by viewModel.recentTranslates.collectAsState()
    val clipboardManager = LocalClipboardManager.current

    Column(modifier = modifier) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 6.dp)) {
            itemsIndexed(recentTranslates) { index, recentTranslate ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            clipboardManager.setText(AnnotatedString(recentTranslate.translatedText))
                        }
                        .padding(horizontal = 12.dp, vertical = 12.dp)
                ) {
                    Text(text = recentTranslate.translatedText, color = Black, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Text(
                        text = recentTranslate.originalText,
                        color = ColorText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Thin
                    )
                }

                if (index != recentTranslates.size - 1) {
                    Divider(color = ColorDivider, thickness = 1.dp)
                }
            }
        }
    }
}