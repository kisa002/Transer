package com.haeyum.shared

import com.haeyum.shared.presentation.Platform

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}