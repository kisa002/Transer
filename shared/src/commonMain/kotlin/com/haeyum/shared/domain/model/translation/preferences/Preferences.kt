package com.haeyum.shared.domain.model.translation.preferences

import com.haeyum.shared.domain.model.translation.languages.Language

data class Preferences(
    val sourceLanguage: Language,
    val targetLanguage: Language
)