package com.haeyum.common.data.mapper

import com.haeyum.common.data.model.recent.RecentTranslate
import com.haeyum.common.domain.model.translation.languages.Language

fun RecentTranslate.toDomain() = com.haeyum.common.domain.model.recent.RecentTranslate(
    idx = idx,
    originalText = originalText,
    translatedText = translatedText,
    sourceLanguage = Language(sourceLanguage.language, sourceLanguage.name),
    targetLanguage = Language(targetLanguage.language, targetLanguage.name)
)

fun List<RecentTranslate>.toDomain() = map { it.toDomain() }