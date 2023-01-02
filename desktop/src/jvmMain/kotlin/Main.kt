// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.haeyum.common.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(
            position = WindowPosition(Alignment.Center),
            size = DpSize(width = 720.dp, height = 400.dp)
        ),
        title = "Transer",
        undecorated = true,
        transparent = true
    ) {
        MenuBar {
            Menu("Tools") {
                Item("Recent", onClick = {})
                Item("Saved", onClick = {})
                Item("Exit", onClick = ::exitApplication)
            }
        }
        App()
    }
}
