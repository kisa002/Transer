package com.haeyum.common.data.model.preferences

import com.haeyum.common.data.model.languages.Language

data class Preferences(
    val nativeLanguage: Language,
    val targetLanguage: Language
)