package com.haeyum.shared.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import com.haeyum.shared.di.DIHelper
import com.haeyum.shared.presentation.component.Header
import com.haeyum.shared.presentation.preferences.PreferencesScreen
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

@Composable
fun iOSPreferencesScreen(modifier: Modifier = Modifier) {
    val viewModel by produceState(
        initialValue = DIHelper().preferencesViewModel,
    ) {
        awaitDispose {
            value.onCleared()
        }
    }

    Box(modifier = modifier) {
        PreferencesScreen(
            header = {
                Header(title = "Preferences")
            },
            supportedLanguages = viewModel.supportedLanguages.collectAsState().value,
            selectedSourceLanguage = viewModel.selectedSourceLanguage.collectAsState().value?.name
                ?: "-",
            selectedTargetLanguage = viewModel.selectedTargetLanguage.collectAsState().value?.name
                ?: "-",
            onSelectedSourceLanguage = viewModel::setSelectedSourceLanguage,
            onSelectedTargetLanguage = viewModel::setSelectedTargetLanguage,
            onClickClearData = viewModel::clearData,
            onClickContact = {
                NSURL.URLWithString("mailto:vnycall74@naver.com")
                    ?.let(UIApplication.sharedApplication::openURL)
                    ?: run(::println)
            },
        )
    }
}