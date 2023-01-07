package di

import presentation.DesktopViewModel
import com.haeyum.common.TranserDatabase
import com.haeyum.common.data.repository.PreferencesRepositoryImpl
import com.haeyum.common.data.repository.RecentTranslateRepositoryImpl
import com.haeyum.common.data.repository.preferences.PreferencesDataSource
import com.haeyum.common.data.repository.preferences.PreferencesDataSourceImpl
import com.haeyum.common.data.repository.recent.RecentTranslateDataSource
import com.haeyum.common.data.repository.recent.RecentTranslateDataSourceImpl
import com.haeyum.common.domain.repository.PreferencesRepository
import com.haeyum.common.domain.repository.RecentTranslateRepository
import com.haeyum.common.domain.usecase.GetPreferencesUseCase
import com.haeyum.common.domain.usecase.SetPreferencesUseCase
import com.haeyum.common.domain.usecase.recent.AddRecentTranslateUseCase
import com.haeyum.common.domain.usecase.recent.DeleteRecentTranslateUseCase
import com.haeyum.common.domain.usecase.recent.GetRecentTranslatesUseCase
import com.haeyum.common.presentation.preferences.PreferencesViewModel
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.File

val desktopModule = module {
    factory {
        DesktopViewModel(
            get(named("DefaultScope")),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    single {
        val driver = JdbcSqliteDriver("jdbc:sqlite:transer.db")
        if (!File("transer.db").exists()) {
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

    singleOf(::GetPreferencesUseCase)
    singleOf(::SetPreferencesUseCase)
    singleOf(::GetRecentTranslatesUseCase)
    singleOf(::AddRecentTranslateUseCase)
    singleOf(::DeleteRecentTranslateUseCase)

    factory {
        PreferencesViewModel(
            get(named("DefaultScope")),
            get(),
            get(),
            get()
        )
    }
}