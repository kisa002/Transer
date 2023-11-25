package com.haeyum.shared

import com.haeyum.shared.data.repository.PreferencesRepositoryImpl
import com.haeyum.shared.data.repository.preferences.PreferencesDataSource
import com.haeyum.shared.data.repository.preferences.PreferencesDataSourceImpl
import com.haeyum.shared.di.commonApiModule
import com.haeyum.shared.di.commonDataModule
import com.haeyum.shared.di.coroutineScopesModule
import com.haeyum.shared.domain.repository.PreferencesRepository
import com.haeyum.shared.domain.usecase.ClearDataUseCase
import com.haeyum.shared.domain.usecase.preferences.GetPreferencesUseCase
import com.haeyum.shared.domain.usecase.preferences.SetPreferencesUseCase
import com.haeyum.shared.domain.usecase.translation.GetSupportedLanguagesUseCase
import com.haeyum.shared.presentation.MainViewModel
import com.haeyum.shared.presentation.mobile.recent.RecentTranslateViewModel
import com.haeyum.shared.presentation.mobile.saved.SavedViewModel
import com.haeyum.shared.presentation.preferences.PreferencesViewModel
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun startKoin() = startKoin {
    modules(commonApiModule, commonDataModule, coroutineScopesModule, mobileModule)
}

val mobileModule = module {
    single<TranserDatabase> {
        val driver = NativeSqliteDriver(TranserDatabase.Schema, "transer.db")
        val database = TranserDatabase(driver)
        database
    }

    singleOf(::PreferencesDataSourceImpl) bind PreferencesDataSource::class
    singleOf(::PreferencesRepositoryImpl) bind PreferencesRepository::class

    factoryOf(::GetPreferencesUseCase)
    factoryOf(::SetPreferencesUseCase)
//    factoryOf(::GetSupportedLanguagesUseCase)
//    factoryOf(::ClearDataUseCase)

    factoryOf(::MainViewModel)
    factoryOf(::PreferencesViewModel)
//    factoryOf(TranslationViewModel)
    factoryOf(::RecentTranslateViewModel)
    factoryOf(::SavedViewModel)
}