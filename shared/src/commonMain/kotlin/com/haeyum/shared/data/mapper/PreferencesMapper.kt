package com.haeyum.shared.data.mapper

import com.haeyum.shared.data.model.preferences.Preferences
import com.haeyum.shared.domain.model.translation.languages.Language

fun Preferences.toDomain() = com.haeyum.shared.domain.model.translation.preferences.Preferences(
    sourceLanguage = Language(
        language = sourceLanguage.language,
        name = sourceLanguage.name
    ),
    targetLanguage = Language(
        language = targetLanguage.language,
        name = targetLanguage.name
    )
)