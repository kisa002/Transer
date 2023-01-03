package com.haeyum.common

import com.haeyum.common.data.model.translate.TranslateResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object KtorClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    coerceInputValues = true
                }
            )
        }
    }

    suspend fun fetchTranslate(q: String, source: String, target: String) : TranslateResponse =
        client.get("https://translation.googleapis.com/language/translate/v2") {
            parameter("q", q)
            parameter("source", source)
            parameter("target", target)
            parameter("key", "AIzaSyA-RrgJ1ot3enCqWboW3zm4Ct37ZkKtO64")
        }.body()
}
