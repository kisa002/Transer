// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.haeyum.common.App
import com.haeyum.common.di.CommonAppKoin

fun main() = application {
    val windowState = rememberWindowState(
        position = WindowPosition(Alignment.Center),
        size = DpSize(width = 720.dp, height = 400.dp)
    )
    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
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
        App(onMinimize = {
            windowState.isMinimized = true
        })
    }

    LaunchedEffect(Unit) {
        CommonAppKoin.startKoin()
    }
}
