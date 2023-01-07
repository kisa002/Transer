package com.haeyum.common.data.model.recent

import com.haeyum.common.data.model.languages.Language

data class RecentTranslate(
    val idx: Int,
    val originalText: String,
    val translatedText: String,
    val sourceLanguage: Language,
    val targetLanguage: Language,
)