import com.haeyum.common.TranserDatabase
import com.haeyum.common.domain.usecase.GetPreferencesUseCase
import com.haeyum.common.domain.usecase.SetPreferencesUseCase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PreferencesDesktopViewModel(
    private val getPreferencesUseCase: GetPreferencesUseCase,
    private val setPreferencesUseCase: SetPreferencesUseCase,
    private val transferDatabase: TranserDatabase
) {
    fun setPreferences(nativeLanguage: String, targetLanguage: String) {
        CoroutineScope(Dispatchers.IO).launch {
            setPreferencesUseCase(nativeLanguage, targetLanguage)
        }
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
//            getPreferencesUseCase().collectLatest {
//                println("DESKTOP DETECT: $it")
//            }
            transferDatabase.preferencesQueries.select().asFlow().mapToOneOrNull().collect {
                println("DETECT VM")
            }

        }
    }
}