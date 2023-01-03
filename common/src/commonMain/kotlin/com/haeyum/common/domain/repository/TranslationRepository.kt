package com.haeyum.common.domain.repository

import com.haeyum.common.domain.model.translation.detect.Detection
import com.haeyum.common.domain.model.translation.languages.Language
import com.haeyum.common.domain.model.translation.translate.Translation

interface TranslationRepository {
    suspend fun translate(q: String, target: String, source: String, key: String): Translation
    suspend fun detectLanguage(q: String, key: String): Detection
    suspend fun getLanguages(target: String, key: String): List<Language>
}