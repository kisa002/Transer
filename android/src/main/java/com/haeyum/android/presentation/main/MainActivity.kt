package com.haeyum.android.presentation.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.haeyum.common.presentation.theme.TranserTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = 0x00000000
//        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            TranserTheme {
                MainScreen(viewModel)
            }
        }
    }
}