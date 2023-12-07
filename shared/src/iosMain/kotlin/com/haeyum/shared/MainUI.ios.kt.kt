@file:OptIn(ExperimentalForeignApi::class)

package com.haeyum.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import com.haeyum.shared.component.CustomBottomNavigation
import com.haeyum.shared.di.DIHelper
import com.haeyum.shared.presentation.component.EventSnackBar
import com.haeyum.shared.presentation.component.rememberEventSnackbraState
import com.haeyum.shared.presentation.iOSPreferencesScreen
import com.haeyum.shared.presentation.iOSRecentTranslateScreen
import com.haeyum.shared.presentation.iOSSavedScreen
import com.haeyum.shared.presentation.iOSTranslateScreen
import com.haeyum.shared.presentation.mobile.MainScreenState
import com.haeyum.shared.presentation.theme.TranserTheme
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

    val eventSnackbarState = rememberEventSnackbraState()

    TranserTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                when (viewModel.screenState.collectAsState().value) {
                    MainScreenState.Translate -> iOSTranslateScreen(
                        modifier = Modifier.fillMaxSize(),
                        viewModel = DIHelper().translateViewModel,
                        onCopiedEvent = eventSnackbarState::showSnackbar
                    )

                    MainScreenState.Recent -> iOSRecentTranslateScreen(
                        modifier = Modifier.fillMaxSize(),
                        onCopiedEvent = eventSnackbarState::showSnackbar
                    )

                    MainScreenState.Saved -> iOSSavedScreen(
                        modifier = Modifier.fillMaxSize(),
                        onCopiedEvent = eventSnackbarState::showSnackbar
                    )

                    MainScreenState.Preferences -> iOSPreferencesScreen(modifier = Modifier.fillMaxSize())
                }

                EventSnackBar(
                    shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
                    backgroundColor = Color(0xFF3270CC),
                    eventSnackbarState = eventSnackbarState
                )
            }

            CustomBottomNavigation(
                viewModel = viewModel,
                screenState = viewModel.screenState.collectAsState().value,
                safeAreaBottomHeight = safeAreaSize?.second?.dp,
            )
        }
    }
}