package com.haeyum.common.data.repository.translation

import com.haeyum.common.data.model.detect.DetectionsResponse
import com.haeyum.common.data.model.languages.LanguagesResponse
import com.haeyum.common.data.model.translate.TranslateResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class TranslationDataSourceImpl(private val client: HttpClient) : TranslationDataSource {
    override suspend fun translate(q: String, target: String, source: String, key: String): TranslateResponse =
        client.get("https://translation.googleapis.com/language/translate/v2") {
            parameter("q", q)
            parameter("source", source)
            parameter("target", target)
            parameter("key", "AIzaSyA-RrgJ1ot3enCqWboW3zm4Ct37ZkKtO64")
        }.body()

    override suspend fun detectLanguage(q: String, key: String): DetectionsResponse =
        client.get("https://translation.googleapis.com/language/translate/v2/detect") {
            parameter("q", q)
            parameter("key", "AIzaSyA-RrgJ1ot3enCqWboW3zm4Ct37ZkKtO64")
        }.body()

    override suspend fun getLanguages(target: String, key: String): LanguagesResponse =
        client.get("https://translation.googleapis.com/language/translate/v2/languages") {
            parameter("target", target)
            parameter("key", "AIzaSyA-RrgJ1ot3enCqWboW3zm4Ct37ZkKtO64")
        }.body()
}