package com.haeyum.shared.data.model.detect

import kotlinx.serialization.Serializable

@Serializable
data class Detection(
    val language: String,
    val isReliable: Boolean,
    val confidence: Float,
)