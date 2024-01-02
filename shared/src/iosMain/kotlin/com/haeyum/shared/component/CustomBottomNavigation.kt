package com.haeyum.shared.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.haeyum.shared.presentation.MainViewModel
import com.haeyum.shared.presentation.mobile.MainBottomNavigationItem
import com.haeyum.shared.presentation.mobile.MainScreenState
import com.haeyum.shared.presentation.theme.ColorLightBlue
import com.haeyum.shared.presentation.vector.History

@Composable
fun CustomBottomNavigation(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    screenState: MainScreenState,
    safeAreaBottomHeight: Dp? = null,
) {
    Column {
        BottomNavigation(backgroundColor = ColorLightBlue) {
            MainBottomNavigationItem(
                selected = screenState == MainScreenState.Translate,
                onClick = viewModel::navigateToTranslate,
                imageVector = Icons.Outlined.Search,
                text = "Translate"
            )
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
        Box(modifier = Modifier.fillMaxWidth().height(safeAreaBottomHeight ?: 0.dp).background(ColorLightBlue))
    }
}