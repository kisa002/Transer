@file:OptIn(ExperimentalForeignApi::class)

package com.haeyum.shared.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haeyum.shared.di.DIHelper
import com.haeyum.shared.presentation.component.Header
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.delay
import platform.UIKit.UIApplication

@Composable
fun iOSTranslateScreen(modifier: Modifier = Modifier) {
    val translateUseCase = remember { DIHelper().translateUseCase }
    val addRecentTranslateUseCase = remember { DIHelper().addRecentTranslateUseCase }

    val (text, setText) = remember { mutableStateOf("") }
    val translatedText by produceState(initialValue = "", key1 = text) {
        if (text.isNotEmpty()) {
            delay(700)
            val texts = text to translateUseCase(text).translatedText
            value = texts.second

            addRecentTranslateUseCase(originalText = texts.first, translatedText = texts.second)
        }
        else {
            value = ""
        }
    }

    Column(modifier = modifier) {
        Header(
            title = "Translate",
        )
        TextField(value = text, onValueChange = setText, modifier = Modifier.fillMaxWidth())
        Text(
            text = translatedText,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
    }
}