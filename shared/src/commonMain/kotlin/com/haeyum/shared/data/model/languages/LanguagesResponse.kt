package com.haeyum.shared.data.model.languages

import kotlinx.serialization.Serializable

@Serializable
data class LanguagesResponse(
    val data: LanguagesData
)