package com.haeyum.shared.data.mapper

import com.haeyum.shared.data.model.recent.RecentTranslate

fun RecentTranslate.toDomain() = com.haeyum.shared.domain.model.recent.RecentTranslate(
    idx = idx,
    originalText = originalText,
    translatedText = translatedText
)

fun List<RecentTranslate>.toDomain() = map { it.toDomain() }