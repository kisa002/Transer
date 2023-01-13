package com.haeyum.android.presentation.main.recent

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import com.haeyum.android.presentation.main.component.ConfirmDeleteAlertDialog
import com.haeyum.android.presentation.main.component.EmptyScreen
import com.haeyum.android.presentation.main.component.LazyTranslatesColumn
import com.haeyum.common.presentation.component.Header
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecentTranslateScreen(modifier: Modifier, viewModel: RecentTranslateViewModel = koinViewModel()) {
    val recentTranslates by viewModel.recentTranslates.collectAsState()
    val pairTranslates by remember(recentTranslates) {
        mutableStateOf(recentTranslates.map { it.translatedText to it.originalText })
    }

    var deleteTranslatedText by remember { mutableStateOf("") }
    var visibleDeleteAlert by remember { mutableStateOf(false) }

    val clipboardManager = LocalClipboardManager.current

    Column(modifier = modifier) {
        Header(title = "Recent")

        if (recentTranslates.isEmpty()) {
            EmptyScreen(text = "Empty Recent")
        } else {
            LazyTranslatesColumn(
                translates = pairTranslates,
                onLongPress = {
                    deleteTranslatedText = it
                    visibleDeleteAlert = true
                },
                onTap = {
                    clipboardManager.setText(it)
                }
            )
        }
    }

    ConfirmDeleteAlertDialog(
        visible = visibleDeleteAlert,
        text = deleteTranslatedText.take(500),
        onConfirm = {
            viewModel.deleteRecentTranslateByTranslatedText(deleteTranslatedText)
            visibleDeleteAlert = false
        },
        onDismiss = { visibleDeleteAlert = false }
    )
}