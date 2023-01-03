// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.haeyum.common.presentation.App
import com.haeyum.common.presentation.DesktopViewModel
import com.haeyum.common.di.DesktopKoin
import org.koin.java.KoinJavaComponent.inject

fun main() {
    DesktopKoin.startKoin()
    val viewModel by inject<DesktopViewModel>(DesktopViewModel::class.java)

    application {
        val windowState = rememberWindowState(
            position = WindowPosition(BiasAlignment(0f, -.3f)),
            size = DpSize(width = 720.dp, height = 400.dp)
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
            App(
                viewModel = viewModel,
                onMinimize = {
                    windowState.isMinimized = true
                }
            )
        }
    }
}
