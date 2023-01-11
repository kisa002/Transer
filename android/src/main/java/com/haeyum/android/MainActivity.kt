package com.haeyum.android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.haeyum.common.domain.model.translation.languages.Language
import com.haeyum.common.domain.usecase.preferences.GetPreferencesUseCase
import com.haeyum.common.domain.usecase.preferences.SetPreferencesUseCase
import com.haeyum.common.presentation.preferences.PreferencesScreen
import com.haeyum.common.presentation.preferences.PreferencesViewModel
import com.haeyum.common.presentation.theme.TranserTheme
import kotlinx.coroutines.flow.firstOrNull
import org.koin.java.KoinJavaComponent.inject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TranserTheme {
                val viewModel by remember {
                    inject<PreferencesViewModel>(PreferencesViewModel::class.java)
                }
                val setPreferencesUseCase by remember {
                    inject<SetPreferencesUseCase>(SetPreferencesUseCase::class.java)
                }
                val getPreferencesUseCase by remember {
                    inject<GetPreferencesUseCase>(GetPreferencesUseCase::class.java)
                }

                PreferencesScreen(
                    supportedLanguages = viewModel.supportedLanguages.collectAsState().value,
                    selectedSourceLanguage = viewModel.selectedSourceLanguage.collectAsState().value?.name ?: "-",
                    selectedTargetLanguage = viewModel.selectedTargetLanguage.collectAsState().value?.name ?: "-",
                    onCloseRequest = {},
                    onSelectedSourceLanguage = viewModel::setSelectedSourceLanguage,
                    onSelectedTargetLanguage = viewModel::setSelectedTargetLanguage,
                    onClickClearData = viewModel::clearData,
                    onClickContact = {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("mailto:vnycall74@naver.com")))
                    }
                )

                DisposableEffect(Unit) {
                    onDispose {
                        viewModel.onDestroy()
                    }
                }

                LaunchedEffect(Unit) {
                    if (getPreferencesUseCase().firstOrNull() == null)
                        setPreferencesUseCase(Language("en", "English"), Language("ko", "Korean"))
                }
            }
        }
    }
}