package di

import com.haeyum.common.TranserDatabase
import com.haeyum.common.data.repository.PreferencesRepositoryImpl
import com.haeyum.common.data.repository.RecentTranslateRepositoryImpl
import com.haeyum.common.data.repository.preferences.PreferencesDataSource
import com.haeyum.common.data.repository.preferences.PreferencesDataSourceImpl
import com.haeyum.common.data.repository.recent.RecentTranslateDataSource
import com.haeyum.common.data.repository.recent.RecentTranslateDataSourceImpl
import com.haeyum.common.domain.repository.PreferencesRepository
import com.haeyum.common.domain.repository.RecentTranslateRepository
import com.haeyum.common.presentation.preferences.PreferencesViewModel
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.koin.core.qualifier.named
import org.koin.dsl.module
import presentation.MainViewModel
import presentation.translation.TranslationViewModel
import java.io.File

val desktopModule = module {
    factory {
        TranslationViewModel(
            get(named("DefaultScope")),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    single {
        val filePath = System.getProperty("user.home") + "/transer.db"
        val driver = JdbcSqliteDriver("jdbc:sqlite:$filePath")
        if (!File(filePath).exists()) {
            TranserDatabase.Schema.create(driver)
        }
        println("TranserDatabase created")

        TranserDatabase(driver)
    }

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

    factory {
        PreferencesViewModel(
            get(named("DefaultScope")),
            get(),
            get(),
            get(),
            get()
        )
    }

    factory {
        MainViewModel(
            get(named("DefaultScope")),
            get(),
            get()
        )
    }
}