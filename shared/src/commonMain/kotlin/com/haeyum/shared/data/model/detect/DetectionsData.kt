package com.haeyum.shared.data.model.detect

import kotlinx.serialization.Serializable

@Serializable
data class DetectionsData(
    val detections: List<List<Detection>>
)