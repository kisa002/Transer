package com.haeyum.transer.presentation.main.saved

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.haeyum.shared.presentation.mobile.saved.SavedScreen
import com.haeyum.shared.presentation.mobile.saved.SavedViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AndroidSavedScreen(modifier: Modifier, viewModel: SavedViewModel = koinViewModel()) {
    SavedScreen(modifier = modifier, viewModel = viewModel, onCopiedEvent = {})
}