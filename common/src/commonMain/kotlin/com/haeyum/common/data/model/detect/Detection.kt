package com.haeyum.common.data.model.detect

data class Detection(
    val language: String,
    val isReliable: Boolean,
    val confidence: Float,
)