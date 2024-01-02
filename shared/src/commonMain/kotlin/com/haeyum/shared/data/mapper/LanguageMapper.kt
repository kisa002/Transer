package com.haeyum.shared.data.mapper

import com.haeyum.shared.data.model.languages.Language

fun Language.toDomain() = com.haeyum.shared.domain.model.translation.languages.Language(
    language = language,
    name = name
)