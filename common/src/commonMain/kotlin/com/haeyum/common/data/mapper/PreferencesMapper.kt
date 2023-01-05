package com.haeyum.common.data.mapper

import com.haeyum.common.data.model.preferences.Preferences

fun Preferences.toDomain() = com.haeyum.common.domain.model.translation.preferences.Preferences(
    nativeLanguage = nativeLanguage,
    targetLanguage = targetLanguage
)