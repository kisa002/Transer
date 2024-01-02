package com.haeyum.shared.domain.repository

import com.haeyum.shared.domain.model.translation.detect.Detection
import com.haeyum.shared.domain.model.translation.languages.Language
import com.haeyum.shared.domain.model.translation.translate.Translation

interface TranslationRepository {
    suspend fun translate(q: String, target: String, source: String): Translation
    suspend fun detectLanguage(q: String): Detection
    suspend fun getLanguages(target: String): List<Language>
}