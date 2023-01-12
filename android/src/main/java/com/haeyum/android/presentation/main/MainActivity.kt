package com.haeyum.android.presentation.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.view.WindowCompat
import com.haeyum.common.domain.model.translation.languages.Language
import com.haeyum.common.domain.usecase.preferences.GetPreferencesUseCase
import com.haeyum.common.domain.usecase.preferences.SetPreferencesUseCase
import com.haeyum.common.presentation.preferences.PreferencesViewModel
import com.haeyum.common.presentation.theme.ColorBackground
import com.haeyum.common.presentation.theme.ColorLightBlue
import com.haeyum.common.presentation.theme.TranserTheme
import kotlinx.coroutines.flow.firstOrNull
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        finish()
//        startActivity(Intent(this, TranslationActivity::class.java))
        window.statusBarColor = 0x00000000
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            TranserTheme {
                val screenState by viewModel.screenState.collectAsState()

                val preferencesViewModel by remember { inject<PreferencesViewModel>() }
                val setPreferencesUseCase by remember { inject<SetPreferencesUseCase>() }
                val getPreferencesUseCase by remember { inject<GetPreferencesUseCase>() }

//                PreferencesScreen(
//                    supportedLanguages = viewModel.supportedLanguages.collectAsState().value,
//                    selectedSourceLanguage = viewModel.selectedSourceLanguage.collectAsState().value?.name ?: "-",
//                    selectedTargetLanguage = viewModel.selectedTargetLanguage.collectAsState().value?.name ?: "-",
//                    onCloseRequest = {},
//                    onSelectedSourceLanguage = viewModel::setSelectedSourceLanguage,
//                    onSelectedTargetLanguage = viewModel::setSelectedTargetLanguage,
//                    onClickClearData = viewModel::clearData,
//                    onClickContact = {
//                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("mailto:vnycall74@naver.com")))
//                    }
//                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ColorBackground)
                        .statusBarsPadding()
                        .navigationBarsPadding()
                ) {
                    RecentTranslateScreen(modifier = Modifier.weight(1f))

                    BottomNavigation(backgroundColor = ColorLightBlue) {
                        BottomNavigationItem(
                            selected = screenState == MainScreenState.Recent,
                            onClick = viewModel::navigateToRecent,
                            imageVector = Icons.Outlined.History,
                            text = "Recent"
                        )
                        BottomNavigationItem(
                            selected = screenState == MainScreenState.Saved,
                            onClick = viewModel::navigateToRecent,
                            imageVector = Icons.Outlined.FavoriteBorder,
                            text = "Saved"
                        )
                        BottomNavigationItem(
                            selected = screenState == MainScreenState.Preferences,
                            onClick = viewModel::navigateToRecent,
                            imageVector = Icons.Outlined.Settings,
                            text = "Preferences"
                        )
                    }
                }

                DisposableEffect(Unit) {
                    onDispose {
                        preferencesViewModel.onDestroy()
                    }
                }

                LaunchedEffect(Unit) {
                    if (getPreferencesUseCase().firstOrNull() == null)
                        setPreferencesUseCase(Language("en", "English"), Language("ko", "Korean"))
                }
            }
        }
    }

    @Composable
    private fun RowScope.BottomNavigationItem(
        selected: Boolean,
        onClick: () -> Unit,
        imageVector: ImageVector,
        text: String
    ) {
        BottomNavigationItem(
            selected = true,
            onClick = onClick,
            icon = {
                Image(imageVector = imageVector, contentDescription = null)
            },
            label = {
                Text(text = text)
            }
        )
    }
}