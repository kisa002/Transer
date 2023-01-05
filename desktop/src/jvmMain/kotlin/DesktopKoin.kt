import com.haeyum.common.di.commonApiModule
import com.haeyum.common.di.commonDataModule
import com.haeyum.common.di.coroutineScopesModule
import org.koin.core.context.startKoin

object DesktopKoin {
    fun startKoin() = startKoin {
        modules(commonApiModule, commonDataModule, coroutineScopesModule, desktopModule)
    }
}