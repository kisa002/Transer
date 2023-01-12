package com.haeyum.android.presentation.main.recent

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import com.haeyum.android.presentation.main.component.LazyTranslatesColumn
import com.haeyum.common.presentation.component.Header
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecentTranslateScreen(modifier: Modifier, viewModel: RecentTranslateViewModel = koinViewModel()) {
    val recentTranslates by viewModel.recentTranslates.collectAsState()
    val pairTranslates by remember(recentTranslates) {
        mutableStateOf(recentTranslates.map { it.translatedText to it.originalText })
    }
    val clipboardManager = LocalClipboardManager.current

    Column(modifier = modifier) {
        Header(title = "Recent")

        LazyTranslatesColumn(translates = pairTranslates) {
            clipboardManager.setText(it)
        }
    }
}