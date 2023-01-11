package com.haeyum.android

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haeyum.common.domain.usecase.translation.TranslateUseCase
import com.haeyum.common.presentation.preferences.PreferencesViewModel
import com.haeyum.common.presentation.theme.Transparent
import com.haeyum.common.presentation.theme.White
import org.koin.java.KoinJavaComponent.inject

class TranslateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val text = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val translateUseCase by inject<TranslateUseCase>(TranslateUseCase::class.java)

        setContent {
            var translatedText by remember {
                mutableStateOf("")
            }

            Box(modifier = Modifier.fillMaxSize().clickable {
                finish()
            }, contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .background(color = White, shape = RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Text(text = translatedText)
                }
            }

            LaunchedEffect(text) {
                translatedText = translateUseCase(text).translatedText
            }
        }

        intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).let {
            println("PangMoo: $it")
        }

//        startService(Intent(this, OverlayService::class.java))
    }
}