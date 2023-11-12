package com.haeyum.shared.data.mapper

import com.haeyum.shared.data.model.detect.DetectionsResponse
import com.haeyum.shared.data.model.languages.LanguagesResponse
import com.haeyum.shared.data.model.translate.TranslateResponse
import com.haeyum.shared.domain.model.translation.detect.Detection
import com.haeyum.shared.domain.model.translation.languages.Language
import com.haeyum.shared.domain.model.translation.translate.Translation

fun TranslateResponse.toDomain() = Translation(data.translations.first().translatedText)
fun DetectionsResponse.toDomain() = Detection(data.detections.first().first().language)
fun LanguagesResponse.toDomain() = data.languages.map { Language(it.language, it.name) }