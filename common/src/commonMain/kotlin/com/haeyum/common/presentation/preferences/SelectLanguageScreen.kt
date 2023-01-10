package com.haeyum.common.presentation.preferences

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haeyum.common.domain.model.translation.languages.Language
import com.haeyum.common.presentation.component.Header
import com.haeyum.common.presentation.theme.*

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
                .padding(horizontal = 8.dp)
                .background(
                    color = ColorBackground,
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(12.dp),
            textStyle = TextStyle(color = Black, fontSize = 14.sp)
        ) { innerTextField ->
            if (keyword.isEmpty())
                Text(text = "Search language", color = ColorIcon, fontSize = 14.sp)

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