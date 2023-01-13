package com.haeyum.common.data.mapper

import com.haeyum.common.data.model.languages.Language

fun Language.toDomain() = com.haeyum.common.domain.model.translation.languages.Language(
    language = language,
    name = name
)