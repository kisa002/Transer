package com.haeyum.common.data.mapper

import com.haeyum.common.data.model.preferences.Preferences
import com.haeyum.common.domain.model.translation.languages.Language

fun Preferences.toDomain() = com.haeyum.common.domain.model.translation.preferences.Preferences(
    nativeLanguage = Language(
        language = nativeLanguage.language,
        name = nativeLanguage.name
    ),
    targetLanguage = Language(
        language = targetLanguage.language,
        name = targetLanguage.name
    )
)