package com.haeyum.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ComposeUIViewController

fun MainUIViewController() = ComposeUIViewController {
    Column {
        Text("Platform: ${getPlatform()}")
        Text("Version: ${getVersion()}")
    }
}