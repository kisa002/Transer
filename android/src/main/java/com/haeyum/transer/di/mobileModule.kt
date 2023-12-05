package com.haeyum.transer.di

import com.haeyum.transer.presentation.main.MainViewModel
import com.haeyum.shared.presentation.mobile.recent.RecentTranslateViewModel
import com.haeyum.transer.presentation.translation.TranslationViewModel
import com.haeyum.shared.TranserDatabase
import com.haeyum.shared.data.repository.PreferencesRepositoryImpl
import com.haeyum.shared.data.repository.preferences.PreferencesDataSource
import com.haeyum.shared.data.repository.preferences.PreferencesDataSourceImpl
import com.haeyum.shared.domain.repository.PreferencesRepository
import com.haeyum.shared.domain.usecase.preferences.GetPreferencesUseCase
import com.haeyum.shared.domain.usecase.preferences.SetPreferencesUseCase
import com.haeyum.shared.presentation.mobile.saved.SavedViewModel
import com.haeyum.shared.presentation.preferences.PreferencesViewModel
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val mobileModule = module {
    single<TranserDatabase> {
        val driver = AndroidSqliteDriver(TranserDatabase.Schema, androidContext(), "transer.db")
        val database = TranserDatabase(driver)
        database
    }

    single<PreferencesDataSource> {
        PreferencesDataSourceImpl(get())
    }

    single<PreferencesRepository> {
        PreferencesRepositoryImpl(get())
    }

    factoryOf(::GetPreferencesUseCase)
    factoryOf(::SetPreferencesUseCase)

    viewModelOf(::MainViewModel)
    viewModelOf(::PreferencesViewModel)
    viewModelOf(::TranslationViewModel)
    viewModelOf(::RecentTranslateViewModel)
    viewModelOf(::SavedViewModel)
}