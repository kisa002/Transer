package com.haeyum.common.data.repository.translation

import com.haeyum.common.data.model.detect.DetectionsResponse
import com.haeyum.common.data.model.languages.LanguagesResponse
import com.haeyum.common.data.model.translate.TranslateResponse

interface TranslationDataSource {
    suspend fun translate(q: String, target: String, source: String): TranslateResponse
    suspend fun detectLanguage(q: String): DetectionsResponse
    suspend fun getLanguages(target: String) : LanguagesResponse
}