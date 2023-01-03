package com.haeyum.common.data.model.languages

import kotlinx.serialization.Serializable

@Serializable
data class LanguagesResponse(
    val data: LanguagesData
)