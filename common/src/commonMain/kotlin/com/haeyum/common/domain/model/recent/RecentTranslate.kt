package com.haeyum.common.domain.model.recent

import com.haeyum.common.domain.model.translation.languages.Language

data class RecentTranslate(
    val idx: Int,
    val originalText: String,
    val translatedText: String
)