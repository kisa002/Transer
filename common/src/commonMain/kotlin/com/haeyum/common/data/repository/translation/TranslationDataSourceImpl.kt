package com.haeyum.common.data.repository.translation

import com.haeyum.common.data.ApiInfo
import com.haeyum.common.data.model.detect.DetectionsResponse
import com.haeyum.common.data.model.languages.LanguagesResponse
import com.haeyum.common.data.model.translate.TranslateResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class TranslationDataSourceImpl(private val client: HttpClient) : TranslationDataSource {
    override suspend fun translate(q: String, target: String, source: String): TranslateResponse =
        client.get(ApiInfo.GOOGLE_TRANSLATE_API_URL) {
            parameter("q", q)
            parameter("source", source)
            parameter("target", target)
            parameter("key", ApiInfo.GOOGLE_TRANSLATE_API_KEY)
        }.body()

    override suspend fun detectLanguage(q: String): DetectionsResponse =
        client.get(ApiInfo.GOOGLE_TRANSLATE_API_DETECT_URL) {
            parameter("q", q)
            parameter("key", ApiInfo.GOOGLE_TRANSLATE_API_KEY)
        }.body()

    override suspend fun getLanguages(target: String): LanguagesResponse =
        client.get(ApiInfo.GOOGLE_TRANSLATE_API_LANGUAGES_URL) {
            parameter("target", target)
            parameter("key", ApiInfo.GOOGLE_TRANSLATE_API_KEY)
        }.body()
}