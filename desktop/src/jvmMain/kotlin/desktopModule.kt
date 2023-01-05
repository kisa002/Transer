import com.haeyum.common.TranserDatabase
import com.haeyum.common.data.repository.PreferencesRepositoryImpl
import com.haeyum.common.data.repository.preferences.PreferencesDataSource
import com.haeyum.common.data.repository.preferences.PreferencesDataSourceImpl
import com.haeyum.common.domain.repository.PreferencesRepository
import com.haeyum.common.domain.usecase.GetPreferencesUseCase
import com.haeyum.common.domain.usecase.SetPreferencesUseCase
import com.haeyum.common.presentation.DesktopViewModel
import com.haeyum.common.presentation.preferences.PreferencesViewModel
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.File

val desktopModule = module {
    factory {
        DesktopViewModel(
            get(named("IOScope")),
            get(),
            get()
        )
    }

    single<TranserDatabase> {
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