package com.haeyum.common.data.mapper

import com.haeyum.common.data.model.recent.RecentTranslate

fun RecentTranslate.toDomain() = com.haeyum.common.domain.model.recent.RecentTranslate(
    idx = idx,
    originalText = originalText,
    translatedText = translatedText
)

fun List<RecentTranslate>.toDomain() = map { it.toDomain() }