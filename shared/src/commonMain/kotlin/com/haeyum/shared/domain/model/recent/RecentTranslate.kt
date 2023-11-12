package com.haeyum.shared.domain.model.recent

import com.haeyum.shared.domain.model.translation.languages.Language

data class RecentTranslate(
    val idx: Int,
    val originalText: String,
    val translatedText: String
)