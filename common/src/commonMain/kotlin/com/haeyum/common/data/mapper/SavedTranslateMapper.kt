package com.haeyum.common.data.mapper

import com.haeyum.common.data.model.saved.SavedTranslate

fun SavedTranslate.toDomain() = com.haeyum.common.domain.model.saved.SavedTranslate(
    idx = idx,
    originalText = originalText,
    translatedText = translatedText
)

fun List<SavedTranslate>.toDomain() = map(SavedTranslate::toDomain)