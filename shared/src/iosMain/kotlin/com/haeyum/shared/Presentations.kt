package com.haeyum.shared

import androidx.compose.ui.window.ComposeUIViewController

// Test
fun PreferencesScreen() = ComposeUIViewController {
    com.haeyum.shared.presentation.preferences.PreferencesScreen(
        modifier = androidx.compose.ui.Modifier,
        header = {},
        supportedLanguages = listOf(),
        selectedSourceLanguage = "English",
        selectedTargetLanguage = "Korean",
        onSelectedSourceLanguage = { },
        onSelectedTargetLanguage = { },
        onClickClearData = { },
        onClickContact = { },
        onNotifyVisibleSelect = { },
    )
}