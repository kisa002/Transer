package com.haeyum.shared.di

import com.haeyum.shared.data.httpClient
import io.ktor.client.*
import org.koin.dsl.module

val commonApiModule = module {
    single<HttpClient> {
        httpClient
    }
}