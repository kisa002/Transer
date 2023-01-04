// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
@file:OptIn(ExperimentalMaterialApi::class)

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.haeyum.common.di.DesktopKoin
import com.haeyum.common.presentation.App
import com.haeyum.common.presentation.DesktopViewModel
import com.haeyum.common.presentation.PreferencesScreen
import org.koin.java.KoinJavaComponent.inject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

fun main() {
    DesktopKoin.startKoin()
    val viewModel by inject<DesktopViewModel>(DesktopViewModel::class.java)
    var players by mutableStateOf(emptyList<String>())

    val database = TranserDatabaseFactory().getDatabase()

    players = database.playerQueries.selectAll().executeAsList().map {
        it.translatedText
    }

    application {
        val windowState = rememberWindowState(
            position = WindowPosition(BiasAlignment(0f, -.3f)),
            size = DpSize(width = 400.dp, height = 560.dp)
//            size = DpSize(width = 720.dp, height = 400.dp)
        )
        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Transer",
            undecorated = true,
            transparent = true,
            resizable = false
        ) {
            MenuBar {
                Menu("Tools") {
                    Item("Recent", onClick = {})
                    Item("Saved", onClick = {})
                    Item("Exit", onClick = ::exitApplication)
                }
            }
            PreferencesScreen()
            return@Window
            App(
                viewModel = viewModel,
                onMinimize = {
                    windowState.isMinimized = true
                }
            )
            AlertDialog(
                onDismissRequest = {},
                title = { Text("Title") },
                text = { Text(text = players.toString()) },
                confirmButton = {
                    Button(onClick = {
                        database.playerQueries.insert(
                            query = Random(System.currentTimeMillis()).nextLong().toString(),
                            translatedText = LocalDateTime.now().format(
                                DateTimeFormatter.ISO_DATE_TIME
                            )
                        )
                        players = database.playerQueries.selectAll().executeAsList().map {
                            it.translatedText
                        }
                    }) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    Button(onClick = {}) {
                        Text("Dismiss")
                    }
                }
            )
        }
    }
}
