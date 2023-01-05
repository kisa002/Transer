package com.haeyum.common.domain.model.translation.preferences

import com.haeyum.common.domain.model.translation.languages.Language

data class Preferences(
    val sourceLanguage: Language,
    val targetLanguage: Language
)