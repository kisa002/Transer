package com.haeyum.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.haeyum.common.presentation.preferences.PreferencesScreen
import com.haeyum.common.presentation.preferences.PreferencesViewModel
import org.koin.java.KoinJavaComponent.inject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val viewModel by remember {
                    inject<PreferencesViewModel>(PreferencesViewModel::class.java)
                }

                PreferencesScreen(
                    supportedLanguages = viewModel.testLanguageDataset,
                    selectedSourceLanguage = viewModel.selectedSourceLanguage.collectAsState().value,
                    selectedTargetLanguage = viewModel.selectedTargetLanguage.collectAsState().value,
                    onCloseRequest = {},
                    onSelectedSourceLanguage = {
                        viewModel.setSelectedSourceLanguage(it)
                    },
                    onSelectedTargetLanguage = {
                        viewModel.setSelectedTargetLanguage(it)
                    }
                )

                DisposableEffect(Unit) {
                    onDispose {
                        viewModel.onDestroy()
                    }
                }
            }
        }
    }
}