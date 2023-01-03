package com.haeyum.common.data.mapper

import com.haeyum.common.data.model.detect.DetectionsResponse
import com.haeyum.common.data.model.languages.LanguagesResponse
import com.haeyum.common.data.model.translate.TranslateResponse
import com.haeyum.common.domain.model.translation.detect.Detection
import com.haeyum.common.domain.model.translation.languages.Language
import com.haeyum.common.domain.model.translation.translate.Translation

fun TranslateResponse.toDomain() = Translation(data.translations.first().translatedText)
fun DetectionsResponse.toDomain() = Detection(data.detections.first().first().language)
fun LanguagesResponse.toDomain() = data.languages.map { Language(it.language, it.name) }