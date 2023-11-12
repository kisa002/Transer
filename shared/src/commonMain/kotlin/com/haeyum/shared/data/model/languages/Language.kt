package com.haeyum.shared.data.model.languages

import kotlinx.serialization.Serializable

@Serializable
data class Language(
    val language: String,
    val name: String
)