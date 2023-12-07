package com.haeyum.transer.presentation.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.haeyum.shared.presentation.component.EventSnackBar
import com.haeyum.shared.presentation.component.rememberEventSnackbraState
import com.haeyum.shared.presentation.mobile.MainBottomNavigationItem
import com.haeyum.shared.presentation.theme.ColorLightBlue
import com.haeyum.shared.presentation.theme.White
import com.haeyum.transer.presentation.main.preferences.AndroidPreferencesScreen
import com.haeyum.transer.presentation.main.recent.AndroidRecentTranslateScreen
import com.haeyum.transer.presentation.main.saved.AndroidSavedScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = koinViewModel(), onRequestFinish: () -> Unit) {
    val screenState by viewModel.screenState.collectAsState()
    val eventSnackbarState = rememberEventSnackbraState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Box(modifier = Modifier.weight(1f)) {
            when (screenState) {
                MainScreenState.Recent -> AndroidRecentTranslateScreen(
                    modifier = Modifier.fillMaxSize(),
                    onCopiedEvent = eventSnackbarState::showSnackbar
                )

                MainScreenState.Saved -> AndroidSavedScreen(
                    modifier = Modifier.fillMaxSize(),
                    onCopiedEvent = eventSnackbarState::showSnackbar
                )

                MainScreenState.Preferences -> AndroidPreferencesScreen(modifier = Modifier.fillMaxSize())
            }
            EventSnackBar(
                shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
                backgroundColor = Color(0xFF3270CC),
                eventSnackbarState = eventSnackbarState
            )
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