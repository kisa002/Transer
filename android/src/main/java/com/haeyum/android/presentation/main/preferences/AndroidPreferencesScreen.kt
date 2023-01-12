package com.haeyum.android.presentation.main.preferences

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.haeyum.common.presentation.component.Header
import com.haeyum.common.presentation.preferences.PreferencesScreen
import com.haeyum.common.presentation.preferences.PreferencesViewModel
import com.haeyum.common.presentation.theme.White
import org.koin.java.KoinJavaComponent.inject

@Composable
fun AndroidPreferencesScreen() {
    val viewModel by remember { inject<PreferencesViewModel>(PreferencesViewModel::class.java) }
    val context = LocalContext.current

    PreferencesScreen(
        modifier = Modifier.fillMaxSize().background(color = White),
        header = {
            Header(title = "Preferences")
        },
        supportedLanguages = viewModel.supportedLanguages.collectAsState().value,
        selectedSourceLanguage = viewModel.selectedSourceLanguage.collectAsState().value?.name ?: "-",
        selectedTargetLanguage = viewModel.selectedTargetLanguage.collectAsState().value?.name ?: "-",
        onSelectedSourceLanguage = viewModel::setSelectedSourceLanguage,
        onSelectedTargetLanguage = viewModel::setSelectedTargetLanguage,
        onClickClearData = viewModel::clearData,
        onClickContact = {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("mailto:vnycall74@naver.com")))
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            viewModel.onDestroy()
        }
    }
}