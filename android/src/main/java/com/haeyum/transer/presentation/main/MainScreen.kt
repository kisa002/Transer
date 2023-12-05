package com.haeyum.transer.presentation.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.haeyum.transer.presentation.main.preferences.AndroidPreferencesScreen
import com.haeyum.transer.presentation.main.recent.AndroidRecentTranslateScreen
import com.haeyum.transer.presentation.main.saved.AndroidSavedScreen
import com.haeyum.shared.presentation.mobile.MainBottomNavigationItem
import com.haeyum.shared.presentation.theme.ColorLightBlue
import com.haeyum.shared.presentation.theme.White
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = koinViewModel(), onRequestFinish: () -> Unit) {
    val screenState by viewModel.screenState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        when (screenState) {
            MainScreenState.Recent -> AndroidRecentTranslateScreen(modifier = Modifier.weight(1f))
            MainScreenState.Saved -> AndroidSavedScreen(modifier = Modifier.weight(1f))
            MainScreenState.Preferences -> Box(modifier = Modifier.weight(1f)) {
                AndroidPreferencesScreen()
            }
        }

        BottomNavigation(backgroundColor = ColorLightBlue) {
            MainBottomNavigationItem(
                selected = screenState == MainScreenState.Recent,
                onClick = viewModel::navigateToRecent,
                imageVector = Icons.Outlined.History,
                text = "Recent"
            )
            MainBottomNavigationItem(
                selected = screenState == MainScreenState.Saved,
                onClick = viewModel::navigateToSaved,
                imageVector = Icons.Outlined.FavoriteBorder,
                text = "Saved"
            )
            MainBottomNavigationItem(
                selected = screenState == MainScreenState.Preferences,
                onClick = viewModel::navigateToPreferences,
                imageVector = Icons.Outlined.Settings,
                text = "Preferences"
            )
        }
    }

    BackHandler(true) {
        onRequestFinish()
    }
}