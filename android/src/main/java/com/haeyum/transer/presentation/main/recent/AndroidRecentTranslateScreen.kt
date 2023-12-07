package com.haeyum.transer.presentation.main.recent

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.haeyum.shared.presentation.mobile.recent.RecentTranslateScreen
import com.haeyum.shared.presentation.mobile.recent.RecentTranslateViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AndroidRecentTranslateScreen(
    modifier: Modifier,
    viewModel: RecentTranslateViewModel = koinViewModel(),
    onCopiedEvent: (String) -> Unit
) {
    RecentTranslateScreen(
        modifier = modifier,
        viewModel = viewModel,
        onCopiedEvent = onCopiedEvent
    )
}