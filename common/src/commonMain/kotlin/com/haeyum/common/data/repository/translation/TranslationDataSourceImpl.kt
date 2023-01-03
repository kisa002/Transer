package com.haeyum.common.data.repository.translation

import com.haeyum.common.data.model.detect.DetectionsResponse
import com.haeyum.common.data.model.languages.LanguagesResponse
import com.haeyum.common.data.model.translate.TranslateResponse

class TranslationDataSourceImpl : TranslationDataSource {
    override suspend fun getTranslate(q: String, target: String, source: String, key: String): TranslateResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getDetect(q: String, key: String): DetectionsResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getLanguages(target: String, key: String): LanguagesResponse {
        TODO("Not yet implemented")
    }
}