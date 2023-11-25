package com.haeyum.shared.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import com.haeyum.shared.di.DIHelper
import com.haeyum.shared.presentation.mobile.recent.RecentTranslateScreen

@Composable
fun iOSRecentTranslateScreen(modifier: Modifier = Modifier) {
    val viewModel by produceState(initialValue = DIHelper().recentTranslateViewModel) {
        awaitDispose {
            value.onCleared()
        }
    }

    RecentTranslateScreen(
        modifier = modifier,
        viewModel = viewModel
    )
}