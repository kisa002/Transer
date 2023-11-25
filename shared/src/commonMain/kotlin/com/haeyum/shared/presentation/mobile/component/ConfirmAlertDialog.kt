package com.haeyum.shared.presentation.mobile.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haeyum.shared.presentation.theme.Black
import com.haeyum.shared.presentation.theme.ColorError
import com.haeyum.shared.presentation.theme.ColorIcon

@Composable
fun ConfirmDeleteAlertDialog(
    visible: Boolean,
    confirmText: String = "Delete",
    dismissText: String = "Cancel",
    title: String = "Do you want to delete it?",
    text: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (visible) {
        AlertDialog(
            onDismissRequest = { /*TODO*/ },
            modifier = Modifier.padding(24.dp),
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text(text = confirmText, color = ColorError)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = dismissText, color = Black)
                }
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = text)
            },
            backgroundColor = ColorIcon,
            contentColor = Black
        )
    }
}