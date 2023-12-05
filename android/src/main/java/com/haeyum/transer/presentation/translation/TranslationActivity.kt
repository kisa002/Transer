package com.haeyum.transer.presentation.translation

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.view.WindowCompat
import com.haeyum.transer.presentation.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalMaterialApi::class)
class TranslationActivity : AppCompatActivity() {
    private val viewModel by viewModel<TranslationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setBackgroundDrawable(ColorDrawable(0))
        window.statusBarColor = 0x00000000
        WindowCompat.setDecorFitsSystemWindows(window, false)

        viewModel.requestTranslation(intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString())

        setContent {
            TranslationScreen(
                onRequestOpen = {
                    startActivity(
                        Intent(
                            this@TranslationActivity,
                            MainActivity::class.java
                        ).apply {
                            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        }
                    )
                },
                onRequestFinish = ::finish,
                viewModel = viewModel
            )
        }
    }
}