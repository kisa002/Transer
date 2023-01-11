package com.haeyum.android

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import com.haeyum.common.presentation.theme.ColorLightBlue

class OverlayService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    private val params = WindowManager.LayoutParams(
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT
    )

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        val manager = getSystemService(WINDOW_SERVICE) as WindowManager
        val manager = applicationContext.getSystemService(WindowManager::class.java)
        manager.addView(
            ComposeView(applicationContext).apply {
                setContent {
                    Box(modifier = Modifier.fillMaxWidth(.8f).background(ColorLightBlue)) {
                        Text("GREAT IDEA!")
                    }
                }
            }, params
        )

        return START_NOT_STICKY
    }
}