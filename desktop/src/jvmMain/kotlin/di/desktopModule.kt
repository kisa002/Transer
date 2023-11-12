package di

import com.haeyum.shared.TranserDatabase
import com.haeyum.shared.data.repository.PreferencesRepositoryImpl
import com.haeyum.shared.data.repository.RecentTranslateRepositoryImpl
import com.haeyum.shared.data.repository.preferences.PreferencesDataSource
import com.haeyum.shared.data.repository.preferences.PreferencesDataSourceImpl
import com.haeyum.shared.data.repository.recent.RecentTranslateDataSource
import com.haeyum.shared.data.repository.recent.RecentTranslateDataSourceImpl
import com.haeyum.shared.domain.repository.PreferencesRepository
import com.haeyum.shared.domain.repository.RecentTranslateRepository
import com.haeyum.shared.presentation.preferences.PreferencesViewModel
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.koin.core.qualifier.named
import org.koin.dsl.module
import MainViewModel
import presentation.window.onboarding.OnboardingViewModel
import presentation.window.translation.TranslationViewModel
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
            get()
        )
    }

    factory {
        OnboardingViewModel(
            get(named("DefaultScope")),
            get(),
            get(),
        )
    }
}