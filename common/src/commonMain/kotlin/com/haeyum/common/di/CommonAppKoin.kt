package com.haeyum.common.di

import org.koin.core.context.startKoin

object CommonAppKoin {
//    private val commonDataModule = module {
//        single<HttpClient> {
//            HttpClient(CIO) {
//                install(ContentNegotiation) {
//                    json(
//                        Json {
//                            prettyPrint = true
//                            isLenient = true
//                            coerceInputValues = true
//                        }
//                    )
//                }
//            }
//        }
//        single<TranslationDataSource> {
//            TranslationDataSourceImpl(get())
//        }
//        single<TranslationRepository> {
//            TranslationRepositoryImpl(get())
//        }
//        single<TranslateUseCase> {
//            TranslateUseCase(get())
//        }
//    }

    fun startKoin() = startKoin {
        modules(commonApiModule, commonDataModule)
    }
}

