package com.haeyum.common.data.model.preferences

import com.haeyum.common.data.model.languages.Language

data class Preferences(
    val sourceLanguage: Language,
    val targetLanguage: Language
)