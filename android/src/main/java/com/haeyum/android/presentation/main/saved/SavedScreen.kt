package com.haeyum.android.presentation.main.saved

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import com.haeyum.android.presentation.main.component.EmptyScreen
import com.haeyum.android.presentation.main.component.LazyTranslatesColumn
import com.haeyum.common.presentation.component.Header
import org.koin.androidx.compose.koinViewModel

@Composable
fun SavedScreen(modifier: Modifier, viewModel: SavedViewModel = koinViewModel()) {
    val savedTranslates by viewModel.savedTranslates.collectAsState()
    val pairTranslates by remember(savedTranslates) {
        mutableStateOf(savedTranslates.map { it.translatedText to it.originalText })
    }

    val clipboardManager = LocalClipboardManager.current

    Column(modifier = modifier) {
        Header(title = "Saved")
        if (savedTranslates.isEmpty()) {
            EmptyScreen(text = "Empty Saved")
        } else {
            LazyTranslatesColumn(pairTranslates) {
                clipboardManager.setText(it)
            }
        }
    }
}