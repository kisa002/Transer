package com.haeyum.shared.data.model.preferences

import com.haeyum.shared.data.model.languages.Language

data class Preferences(
    val sourceLanguage: Language,
    val targetLanguage: Language
)