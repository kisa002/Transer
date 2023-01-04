package com.haeyum.common.presentation

import com.haeyum.common.domain.usecase.GetSupportedLanguagesUseCase
import com.haeyum.common.domain.usecase.TranslateUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class)
class DesktopViewModel(
    private val ioScope: CoroutineScope,
    private val translateUseCase: TranslateUseCase,
    private val getSupportedLanguagesUseCase: GetSupportedLanguagesUseCase
) {
    private val _isRequesting = MutableStateFlow(false)
    val isRequesting: StateFlow<Boolean> = _isRequesting

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    val translatedText = query
        .transformLatest { query ->
            emit(
                if (query.isEmpty()) {
                    _isRequesting.value = false
                    ""
                } else {
                    delay(300)
                    _isRequesting.value = true
                    delay(700)
                    translateUseCase(q = query, source = "en", target = "ko", key = "").translatedText
                }
            )
        }
        .onEach {
            _isRequesting.value = false
        }
        .catch {
            _isRequesting.value = false
            it.printStackTrace()
        }
        .stateIn(ioScope, SharingStarted.Lazily, "")

    fun setQuery(query: String) {
        _query.value = query
    }

    init {
//        ioScope.launch {
//            kotlin.runCatching {
//                getSupportedLanguagesUseCase(target = "en", key = "")
//            }.onSuccess {
//                println("Languages size: ${it.size}")
//            }.onFailure {
//                it.printStackTrace()
//            }
//        }
    }
}