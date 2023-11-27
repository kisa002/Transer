@file:OptIn(ExperimentalForeignApi::class)

package com.haeyum.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import com.haeyum.shared.component.CustomBottomNavigation
import com.haeyum.shared.di.DIHelper
import com.haeyum.shared.presentation.iOSRecentTranslateScreen
import com.haeyum.shared.presentation.iOSSavedScreen
import com.haeyum.shared.presentation.iOSTranslateScreen
import com.haeyum.shared.presentation.mobile.MainScreenState
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.UIApplication

fun MainUIViewController() = ComposeUIViewController {
    val viewModel = remember {
        DIHelper().mainViewModel
    }

    val safeAreaSize = remember {
        UIApplication.sharedApplication.keyWindow?.safeAreaInsets()?.useContents {
            top.toFloat() to bottom.toFloat()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        when (viewModel.screenState.collectAsState().value) {
            MainScreenState.Translate -> iOSTranslateScreen(modifier = Modifier.weight(1f))
            MainScreenState.Recent -> iOSRecentTranslateScreen(modifier = Modifier.weight(1f))
            MainScreenState.Saved -> iOSSavedScreen(modifier = Modifier.weight(1f))
            MainScreenState.Preferences -> iOSPreferencesScreen(modifier = Modifier.weight(1f))
        }
        CustomBottomNavigation(
            viewModel = viewModel,
            screenState = viewModel.screenState.collectAsState().value,
            safeAreaBottomHeight = safeAreaSize?.second?.dp,
        )
    }
}