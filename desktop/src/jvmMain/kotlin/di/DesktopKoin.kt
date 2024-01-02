package di

import com.haeyum.shared.di.commonApiModule
import com.haeyum.shared.di.commonDataModule
import com.haeyum.shared.di.coroutineScopesModule
import org.koin.core.context.startKoin

object DesktopKoin {
    fun startKoin() = startKoin {
        modules(commonApiModule, commonDataModule, coroutineScopesModule, desktopModule)
    }
}