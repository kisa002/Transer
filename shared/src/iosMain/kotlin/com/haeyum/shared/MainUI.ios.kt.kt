@file:OptIn(ExperimentalForeignApi::class)

package com.haeyum.shared

import androidx.compose.animation.*
import androidx.compose.animation.core.EaseOutExpo
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.ComposeUIViewController
import com.haeyum.shared.component.CustomBottomNavigation
import com.haeyum.shared.di.DIHelper
import com.haeyum.shared.presentation.iOSPreferencesScreen
import com.haeyum.shared.presentation.iOSRecentTranslateScreen
import com.haeyum.shared.presentation.iOSSavedScreen
import com.haeyum.shared.presentation.iOSTranslateScreen
import com.haeyum.shared.presentation.mobile.MainScreenState
import com.haeyum.shared.presentation.theme.TranserTheme
import com.haeyum.shared.presentation.theme.White
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    val coroutineScope = rememberCoroutineScope()

    val (snackbarState, setSnackbarState) = remember { mutableStateOf<String?>(null) }
    val showSnackbar: (String?) -> Unit = remember {
        {
            coroutineScope.launch {
                setSnackbarState(it)
                delay(2000)
                setSnackbarState(null)
            }
        }
    }


    TranserTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                when (viewModel.screenState.collectAsState().value) {
                    MainScreenState.Translate -> iOSTranslateScreen(
                        modifier = Modifier.fillMaxSize(),
                        viewModel = DIHelper().translateViewModel,
                        onCopiedEvent = showSnackbar
                    )

                    MainScreenState.Recent -> iOSRecentTranslateScreen(
                        modifier = Modifier.fillMaxSize(),
                        onCopiedEvent = showSnackbar
                    )

                    MainScreenState.Saved -> iOSSavedScreen(
                        modifier = Modifier.fillMaxSize(),
                        onCopiedEvent = showSnackbar
                    )

                    MainScreenState.Preferences -> iOSPreferencesScreen(modifier = Modifier.fillMaxSize())
                }

                EventSnackBar(snackbarState)
            }

            CustomBottomNavigation(
                viewModel = viewModel,
                screenState = viewModel.screenState.collectAsState().value,
                safeAreaBottomHeight = safeAreaSize?.second?.dp,
            )
        }
    }
}

@Composable
fun BoxScope.EventSnackBar(snackbarState: String?) {
    AnimatedVisibility(
        visible = snackbarState != null,
        modifier = Modifier.align(Alignment.BottomCenter),
        enter = fadeIn() + slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(
                durationMillis = 1000,
                easing = EaseOutExpo
            )
        ),
        exit = fadeOut() + slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(
                durationMillis = 1000,
                easing = EaseOutExpo
            )
        )
    ) {
        Snackbar(
            backgroundColor = Color(0xFF3270CC),
            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
        ) {
            Text(
                text = snackbarState ?: "",
                color = White,
                fontSize = 16.sp
            )
        }
    }
}
