package com.haeyum.shared.data.repository.translation

import com.haeyum.shared.data.model.detect.DetectionsResponse
import com.haeyum.shared.data.model.languages.LanguagesResponse
import com.haeyum.shared.data.model.translate.TranslateResponse

interface TranslationDataSource {
    suspend fun translate(q: String, target: String, source: String): TranslateResponse
    suspend fun detectLanguage(q: String): DetectionsResponse
    suspend fun getLanguages(target: String) : LanguagesResponse
}