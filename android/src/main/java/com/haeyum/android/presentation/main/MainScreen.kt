package com.haeyum.android.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.haeyum.android.presentation.main.preferences.AndroidPreferencesScreen
import com.haeyum.android.presentation.main.recent.RecentTranslateScreen
import com.haeyum.android.presentation.main.saved.SavedScreen
import com.haeyum.common.presentation.theme.ColorBackground
import com.haeyum.common.presentation.theme.ColorLightBlue
import com.haeyum.common.presentation.theme.White
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = koinViewModel()) {
    val screenState by viewModel.screenState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        when (screenState) {
            MainScreenState.Recent -> RecentTranslateScreen(modifier = Modifier.weight(1f))
            MainScreenState.Saved -> SavedScreen(modifier = Modifier.weight(1f))
            MainScreenState.Preferences -> Box(modifier = Modifier.weight(1f)) {
                AndroidPreferencesScreen()
            }
        }

        BottomNavigation(backgroundColor = ColorLightBlue) {
            BottomNavigationItem(
                selected = screenState == MainScreenState.Recent,
                onClick = viewModel::navigateToRecent,
                imageVector = Icons.Outlined.History,
                text = "Recent"
            )
            BottomNavigationItem(
                selected = screenState == MainScreenState.Saved,
                onClick = viewModel::navigateToSaved,
                imageVector = Icons.Outlined.FavoriteBorder,
                text = "Saved"
            )
            BottomNavigationItem(
                selected = screenState == MainScreenState.Preferences,
                onClick = viewModel::navigateToPreferences,
                imageVector = Icons.Outlined.Settings,
                text = "Preferences"
            )
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
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = if (selected) Color.White else Color.DarkGray
            )
        },
        label = {
            Text(text = text, color = if (selected) Color.White else Color.DarkGray)
        }
    )
}