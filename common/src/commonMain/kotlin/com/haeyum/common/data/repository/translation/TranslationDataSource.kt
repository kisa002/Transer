package com.haeyum.common.data.repository.translation

import com.haeyum.common.data.model.detect.DetectionsResponse
import com.haeyum.common.data.model.languages.LanguagesResponse
import com.haeyum.common.data.model.translate.TranslateResponse

interface TranslationDataSource {
    suspend fun translate(q: String, target: String, source: String, key: String): TranslateResponse
    suspend fun detectLanguage(q: String, key: String): DetectionsResponse
    suspend fun getLanguages(target: String, key: String) : LanguagesResponse
}