package com.haeyum.shared.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import com.haeyum.shared.di.DIHelper
import com.haeyum.shared.presentation.mobile.saved.SavedScreen

@Composable
fun iOSSavedScreen(modifier: Modifier = Modifier, onCopiedEvent: (String) -> Unit) {
    val viewModel by produceState(initialValue = DIHelper().savedViewModel) {
        awaitDispose {
            value.onCleared()
        }
    }

    SavedScreen(
        modifier = modifier,
        viewModel = viewModel,
        onCopiedEvent = onCopiedEvent
    )
}