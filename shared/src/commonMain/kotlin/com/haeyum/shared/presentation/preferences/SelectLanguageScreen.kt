package com.haeyum.shared.presentation.preferences

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haeyum.shared.domain.model.translation.languages.Language
import com.haeyum.shared.getPlatform
import com.haeyum.shared.presentation.Platform
import com.haeyum.shared.presentation.component.Header
import com.haeyum.shared.presentation.theme.Black
import com.haeyum.shared.presentation.theme.ColorBackground
import com.haeyum.shared.presentation.theme.ColorIcon
import com.haeyum.shared.presentation.theme.ColorSecondaryDivider
import com.haeyum.shared.presentation.theme.White

@Composable
fun SelectLanguageScreen(
    title: String,
    languages: List<Language>,
    onDismissRequest: () -> Unit,
    onSelectedLanguage: (Language) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    var keyword by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = White, shape = RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = ColorSecondaryDivider, shape = RoundedCornerShape(8.dp))
    ) {
        Header(title = title, imageVector = Icons.Default.ArrowDropDown, onClick = onDismissRequest)
        BasicTextField(
            value = keyword,
            onValueChange = { keyword = it },
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 8.dp)
                .background(
                    color = ColorBackground,
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(12.dp),
            textStyle = TextStyle(color = Black, fontSize = 14.sp),
            cursorBrush = SolidColor(Black)
        ) { innerTextField ->
            if (keyword.isEmpty()) {
                Text(text = "Search language", color = ColorIcon, fontSize = 14.sp)
            }

            innerTextField()
        }
        LazyColumn(modifier = Modifier.weight(1f).padding(vertical = 12.dp)) {
            items(languages) { language ->
                if (language.name.contains(keyword, true)) {
                    LanguageItem(language = language, onSelectedLanguage = onSelectedLanguage)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        if (getPlatform() == Platform.Desktop)
            focusRequester.requestFocus()
    }
}

@Composable
private fun LanguageItem(onSelectedLanguage: (Language) -> Unit, language: Language) {
    TextButton(
        onClick = {
            onSelectedLanguage(language)
        }
    ) {
        Text(
            text = language.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            color = Black,
            fontSize = 14.sp
        )
    }
}