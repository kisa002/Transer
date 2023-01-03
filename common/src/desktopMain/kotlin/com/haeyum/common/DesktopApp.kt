// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.haeyum.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.haeyum.common.presentation.App
import com.haeyum.common.presentation.DesktopViewModel
import org.koin.java.KoinJavaComponent.inject

@Preview
@Composable
fun AppPreview() {
    val desktopViewModel by inject<DesktopViewModel>(DesktopViewModel::class.java)
    App(desktopViewModel)
}