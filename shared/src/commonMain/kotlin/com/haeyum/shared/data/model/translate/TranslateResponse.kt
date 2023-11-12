package com.haeyum.shared.data.model.translate

import kotlinx.serialization.Serializable

@Serializable
data class TranslateResponse(
    val data: TranslateData
)