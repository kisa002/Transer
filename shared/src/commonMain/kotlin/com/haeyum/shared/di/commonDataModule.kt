package com.haeyum.shared.di

import com.haeyum.shared.data.repository.PreferencesRepositoryImpl
import com.haeyum.shared.data.repository.RecentTranslateRepositoryImpl
import com.haeyum.shared.data.repository.SavedTranslateRepositoryImpl
import com.haeyum.shared.data.repository.TranslationRepositoryImpl
import com.haeyum.shared.data.repository.preferences.PreferencesDataSource
import com.haeyum.shared.data.repository.preferences.PreferencesDataSourceImpl
import com.haeyum.shared.data.repository.recent.RecentTranslateDataSource
import com.haeyum.shared.data.repository.recent.RecentTranslateDataSourceImpl
import com.haeyum.shared.data.repository.saved.SavedTranslateDataSource
import com.haeyum.shared.data.repository.saved.SavedTranslateDataSourceImpl
import com.haeyum.shared.data.repository.translation.TranslationDataSource
import com.haeyum.shared.data.repository.translation.TranslationDataSourceImpl
import com.haeyum.shared.domain.repository.PreferencesRepository
import com.haeyum.shared.domain.repository.RecentTranslateRepository
import com.haeyum.shared.domain.repository.SavedTranslateRepository
import com.haeyum.shared.domain.repository.TranslationRepository
import com.haeyum.shared.domain.usecase.*
import com.haeyum.shared.domain.usecase.preferences.GetPreferencesUseCase
import com.haeyum.shared.domain.usecase.preferences.SetPreferencesUseCase
import com.haeyum.shared.domain.usecase.recent.*
import com.haeyum.shared.domain.usecase.saved.*
import com.haeyum.shared.domain.usecase.translation.DetectLanguageUseCase
import com.haeyum.shared.domain.usecase.translation.GetSupportedLanguagesUseCase
import com.haeyum.shared.domain.usecase.translation.TranslateUseCase
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
    singleOf(::ClearRecentTranslatesUseCase)
    singleOf(::ClearSavedTranslatesUseCase)
    singleOf(::ClearDataUseCase)

    single<PreferencesDataSource> {
        PreferencesDataSourceImpl(get())
    }

    single<PreferencesRepository> {
        PreferencesRepositoryImpl(get())
    }

    single<RecentTranslateDataSource> {
        RecentTranslateDataSourceImpl(get())
    }

    single<RecentTranslateRepository> {
        RecentTranslateRepositoryImpl(get())
    }
//    singleOf(::GetRecentTranslatesUseCase)
//    singleOf(::AddRecentTranslateUseCase)
//    singleOf(::DeleteRecentTranslateUseCase)
//    singleOf(::PreferencesDataSourceImpl) bind PreferencesDataSourceImpl::class
//    singleOf(::PreferencesRepositoryImpl) bind PreferencesRepository::class
//    singleOf(::GetPreferencesUseCase)
}