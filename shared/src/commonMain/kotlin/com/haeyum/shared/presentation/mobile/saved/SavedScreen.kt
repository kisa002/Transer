package com.haeyum.shared.presentation.mobile.saved

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import com.haeyum.shared.presentation.component.Header
import com.haeyum.shared.presentation.mobile.component.ConfirmDeleteAlertDialog
import com.haeyum.shared.presentation.mobile.component.EmptyScreen
import com.haeyum.shared.presentation.mobile.component.LazyTranslatesColumn

@Composable
fun SavedScreen(modifier: Modifier, viewModel: SavedViewModel) {
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