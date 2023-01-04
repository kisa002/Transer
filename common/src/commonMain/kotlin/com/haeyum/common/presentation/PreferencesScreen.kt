@file:OptIn(ExperimentalMaterialApi::class)

package com.haeyum.common.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.java.KoinJavaComponent
import java.util.*

@Composable
fun PreferencesScreen(viewModel: PreferencesViewModel) {
    var isShowSelectNativeLanguage by remember { mutableStateOf(false) }
    var isShowSelectTargetLanguage by remember { mutableStateOf(false) }

    var searchNativeLanguage by remember { mutableStateOf("") }
    var searchTargetLanguage by remember { mutableStateOf("") }

    val selectedNativeLanguage = viewModel.selectedNativeLanguage.collectAsState().value
    val selectedTargetLanguage = viewModel.selectedTargetLanguage.collectAsState().value

//    val list = listOf("Korean", "English", "Japanese", "Chinese", "French", "German", "Russian", "Spanish")
    val list by viewModel.languages.collectAsState()

    val nativeLanguageFocusRequester = remember { FocusRequester() }
    val targetLanguageFocusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = Color(0xFFE5E5E5), shape = RoundedCornerShape(8.dp))
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(color = Color(0xFFFBFBFB), shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "Preferences",
                modifier = Modifier.align(Alignment.Center),
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = {}, modifier = Modifier.align(Alignment.CenterEnd)) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color(0xFFADADAD))
            }
        }
        Divider(color = Color(0xFFE5E5E5))
        Spacer(modifier = Modifier.height(16.dp))

        Section(text = "Language") {
            Item(
                key = "Native Language",
                value = selectedNativeLanguage,
                iconVector = Icons.Default.ArrowDropDown,
                contentDescription = "Native Language"
            ) {
                isShowSelectNativeLanguage = true
            }
            DropdownMenu(
                expanded = isShowSelectNativeLanguage,
                onDismissRequest = {
                    searchNativeLanguage = ""
                    isShowSelectNativeLanguage = false
                },
                modifier = Modifier.align(Alignment.End),
                offset = DpOffset(224.dp, -(48).dp)
            ) {
                Column(modifier = Modifier.size(width = 160.dp, height = 256.dp)) {
                    BasicTextField(
                        value = searchNativeLanguage,
                        onValueChange = { searchNativeLanguage = it },
                        modifier = Modifier
                            .focusRequester(nativeLanguageFocusRequester)
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .background(
                                color = Color(0xFFEFEFEF),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(12.dp),
                        textStyle = TextStyle(color = Color.Black, fontSize = 14.sp)
                    ) { innerTextField ->
                        if (searchNativeLanguage.isEmpty())
                            Text(text = "Search language", color = Color(0xFFADADAD), fontSize = 14.sp)

                        innerTextField()
                    }

                    Column(modifier = Modifier.padding(8.dp).weight(1f).verticalScroll(rememberScrollState())) {
                        list.forEach {
                            if (it.lowercase(Locale.getDefault()).contains(searchNativeLanguage.lowercase())) {
                                DropdownMenuItem(onClick = {
                                    viewModel.setSelectedNativeLanguage(it)
                                    isShowSelectNativeLanguage = false
                                }) {
                                    Text(text = it, color = Color.Black, fontSize = 14.sp)
                                }
                            }
                        }
                    }
                }
            }

            Item(
                key = "Target Language",
                value = selectedTargetLanguage,
                iconVector = Icons.Default.ArrowDropDown,
                contentDescription = "Target Language"
            ) {
                isShowSelectTargetLanguage = true
            }
            DropdownMenu(
                expanded = isShowSelectTargetLanguage,
                onDismissRequest = {
                    searchTargetLanguage = ""
                    isShowSelectTargetLanguage = false
                },
                modifier = Modifier.align(Alignment.End),
                offset = DpOffset(224.dp, 0.dp)
            ) {
                Column(modifier = Modifier.size(width = 160.dp, height = 256.dp)) {
                    BasicTextField(
                        value = searchTargetLanguage,
                        onValueChange = { searchTargetLanguage = it },
                        modifier = Modifier
                            .focusRequester(targetLanguageFocusRequester)
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .background(
                                color = Color(0xFFEFEFEF),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(12.dp),
                        textStyle = TextStyle(color = Color.Black, fontSize = 14.sp)
                    ) { innerTextField ->
                        if (searchTargetLanguage.isEmpty())
                            Text(text = "Search language", color = Color(0xFFADADAD), fontSize = 14.sp)

                        innerTextField()
                    }

                    Column(modifier = Modifier.padding(8.dp).weight(1f).verticalScroll(rememberScrollState())) {
                        list.forEach {
                            if (it.lowercase(Locale.getDefault()).contains(searchTargetLanguage.lowercase())) {
                                DropdownMenuItem(onClick = {
                                    viewModel.setSelectedTargetLanguage(it)
                                    isShowSelectTargetLanguage = false
                                }) {
                                    Text(text = it, color = Color.Black, fontSize = 14.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        Section(text = "Information") {
            Item(key = "Contact")
            Item(key = "Version", value = "1.0.0")
        }
    }

    LaunchedEffect(isShowSelectNativeLanguage) {
        if (isShowSelectNativeLanguage) {
            nativeLanguageFocusRequester.requestFocus()
        }
    }

    LaunchedEffect(isShowSelectTargetLanguage) {
        if (isShowSelectTargetLanguage) {
            targetLanguageFocusRequester.requestFocus()
        }
    }
}

@Composable
private fun Section(text: String, content: @Composable () -> Unit) {
    Column {
        Text(
            text = text,
            modifier = Modifier.padding(top = 24.dp).padding(horizontal = 16.dp),
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Divider(modifier = Modifier.padding(top = 24.dp), color = Color(0xFFE5E5E5))
        Spacer(modifier = Modifier.height(12.dp))
        content()
    }
}

@Composable
private fun Item(
    key: String,
    value: String = "",
    iconVector: ImageVector? = null,
    contentDescription: String? = null,
    onClick: () -> Unit = {}
) {
    TextButton(
        onClick = onClick,
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = key,
                color = Color(0xFF333333),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Row(
                modifier = Modifier.height(32.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    color = Color(0xFF333333),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                if (iconVector != null) {
                    IconButton(onClick = onClick, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = iconVector,
                            contentDescription = contentDescription,
                            tint = Color(0xFFADADAD)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PreferencesPreviewScreen() {
    MaterialTheme {
        val preferencesViewModel by KoinJavaComponent.inject<PreferencesViewModel>(PreferencesViewModel::class.java)

        PreferencesScreen(preferencesViewModel)
    }
}