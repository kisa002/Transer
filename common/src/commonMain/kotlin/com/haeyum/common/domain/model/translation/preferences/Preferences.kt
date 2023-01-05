package com.haeyum.common.domain.model.translation.preferences

import com.haeyum.common.domain.model.translation.languages.Language

data class Preferences(
    val nativeLanguage: Language,
    val targetLanguage: Language
)