package com.haeyum.common.di

import com.haeyum.common.data.repository.SavedTranslateRepositoryImpl
import com.haeyum.common.data.repository.TranslationRepositoryImpl
import com.haeyum.common.data.repository.saved.SavedTranslateDataSource
import com.haeyum.common.data.repository.saved.SavedTranslateDataSourceImpl
import com.haeyum.common.data.repository.translation.TranslationDataSource
import com.haeyum.common.data.repository.translation.TranslationDataSourceImpl
import com.haeyum.common.domain.repository.SavedTranslateRepository
import com.haeyum.common.domain.repository.TranslationRepository
import com.haeyum.common.domain.usecase.DetectLanguageUseCase
import com.haeyum.common.domain.usecase.GetSupportedLanguagesUseCase
import com.haeyum.common.domain.usecase.TranslateUseCase
import com.haeyum.common.domain.usecase.saved.AddSavedTranslateUseCase
import com.haeyum.common.domain.usecase.saved.DeleteSavedTranslateUseCase
import com.haeyum.common.domain.usecase.saved.GetSavedTranslatesUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val commonDataModule = module {
    singleOf(::TranslationDataSourceImpl) bind TranslationDataSource::class
    singleOf(::TranslationRepositoryImpl) bind TranslationRepository::class
    singleOf(::TranslateUseCase)
    singleOf(::DetectLanguageUseCase)
    singleOf(::GetSupportedLanguagesUseCase)
    singleOf(::SavedTranslateRepositoryImpl) bind SavedTranslateRepository::class
    singleOf(::SavedTranslateDataSourceImpl) bind SavedTranslateDataSource::class
    singleOf(::GetSavedTranslatesUseCase)
    singleOf(::AddSavedTranslateUseCase)
    singleOf(::DeleteSavedTranslateUseCase)
//    singleOf(::GetRecentTranslatesUseCase)
//    singleOf(::AddRecentTranslateUseCase)
//    singleOf(::DeleteRecentTranslateUseCase)
//    singleOf(::PreferencesDataSourceImpl) bind PreferencesDataSourceImpl::class
//    singleOf(::PreferencesRepositoryImpl) bind PreferencesRepository::class
//    singleOf(::GetPreferencesUseCase)
}