package com.haeyum.shared.data.model.detect

import kotlinx.serialization.Serializable

@Serializable
data class DetectionsResponse(
    val data: DetectionsData
)