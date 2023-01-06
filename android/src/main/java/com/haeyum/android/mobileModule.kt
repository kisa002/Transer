package com.haeyum.android

import com.haeyum.common.TranserDatabase
import com.haeyum.common.data.repository.PreferencesRepositoryImpl
import com.haeyum.common.data.repository.preferences.PreferencesDataSource
import com.haeyum.common.data.repository.preferences.PreferencesDataSourceImpl
import com.haeyum.common.domain.repository.PreferencesRepository
import com.haeyum.common.domain.usecase.GetPreferencesUseCase
import com.haeyum.common.domain.usecase.SetPreferencesUseCase
import com.haeyum.common.presentation.preferences.PreferencesViewModel
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
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

    factory {
        PreferencesViewModel(
            get(named("IOScope")),
            get(),
            get(),
            get()
        )
    }
}