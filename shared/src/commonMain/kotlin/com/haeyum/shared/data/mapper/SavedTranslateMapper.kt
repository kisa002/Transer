package com.haeyum.shared.data.mapper

import com.haeyum.shared.data.model.saved.SavedTranslate

fun SavedTranslate.toDomain() = com.haeyum.shared.domain.model.saved.SavedTranslate(
    idx = idx,
    originalText = originalText,
    translatedText = translatedText
)

fun List<SavedTranslate>.toDomain() = map(SavedTranslate::toDomain)