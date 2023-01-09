package com.haeyum.common.di

import com.haeyum.common.data.repository.SavedTranslateRepositoryImpl
import com.haeyum.common.data.repository.TranslationRepositoryImpl
import com.haeyum.common.data.repository.saved.SavedTranslateDataSource
import com.haeyum.common.data.repository.saved.SavedTranslateDataSourceImpl
import com.haeyum.common.data.repository.translation.TranslationDataSource
import com.haeyum.common.data.repository.translation.TranslationDataSourceImpl
import com.haeyum.common.domain.repository.SavedTranslateRepository
import com.haeyum.common.domain.repository.TranslationRepository
import com.haeyum.common.domain.usecase.*
import com.haeyum.common.domain.usecase.recent.*
import com.haeyum.common.domain.usecase.saved.AddSavedTranslateUseCase
import com.haeyum.common.domain.usecase.saved.DeleteSavedTranslateUseCase
import com.haeyum.common.domain.usecase.saved.GetSavedTranslatesUseCase
import com.haeyum.common.domain.usecase.saved.IsExistsSavedTranslateUseCase
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
    singleOf(::GetPreferencesUseCase)
    singleOf(::SetPreferencesUseCase)
    singleOf(::GetRecentTranslatesUseCase)
    singleOf(::AddRecentTranslateUseCase)
    singleOf(::DeleteRecentTranslateByIdxUseCase)
    singleOf(::DeleteRecentTranslateByTranslatedTextUseCase)
    singleOf(::DeleteAndAddRecentTranslateUseCase)
    singleOf(::GetSavedTranslatesUseCase)
    singleOf(::AddSavedTranslateUseCase)
    singleOf(::DeleteSavedTranslateUseCase)
    singleOf(::IsExistsSavedTranslateUseCase)
//    singleOf(::GetRecentTranslatesUseCase)
//    singleOf(::AddRecentTranslateUseCase)
//    singleOf(::DeleteRecentTranslateUseCase)
//    singleOf(::PreferencesDataSourceImpl) bind PreferencesDataSourceImpl::class
//    singleOf(::PreferencesRepositoryImpl) bind PreferencesRepository::class
//    singleOf(::GetPreferencesUseCase)
}