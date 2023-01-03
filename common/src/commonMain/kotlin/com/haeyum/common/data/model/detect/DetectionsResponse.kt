package com.haeyum.common.data.model.detect

import kotlinx.serialization.Serializable

@Serializable
data class DetectionsResponse(
    val data: DetectionsData
)