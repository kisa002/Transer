package com.haeyum.android.presentation.main.saved

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
fun SavedScreen(modifier: Modifier, viewModel: SavedViewModel = koinViewModel()) {
    val savedTranslates by viewModel.savedTranslates.collectAsState()
    val pairTranslates by remember(savedTranslates) {
        mutableStateOf(savedTranslates.map { it.translatedText to it.originalText })
    }

    var deleteTranslatedText by remember { mutableStateOf("") }
    var visibleDeleteAlert by remember { mutableStateOf(false) }

    val clipboardManager = LocalClipboardManager.current

    Column(modifier = modifier) {
        Header(title = "Saved")

        if (savedTranslates.isEmpty()) {
            EmptyScreen(text = "Empty Saved")
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
            viewModel.deleteSavedTranslate(deleteTranslatedText)
            visibleDeleteAlert = false
        },
        onDismiss = { visibleDeleteAlert = false }
    )
}