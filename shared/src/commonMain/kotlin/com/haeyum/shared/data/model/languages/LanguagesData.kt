package com.haeyum.shared.data.model.languages

import kotlinx.serialization.Serializable

@Serializable
data class LanguagesData(
    val languages: List<Language>
)