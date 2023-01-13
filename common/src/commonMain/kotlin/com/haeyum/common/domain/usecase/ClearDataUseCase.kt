package com.haeyum.common.domain.usecase

import com.haeyum.common.domain.usecase.recent.ClearRecentTranslatesUseCase
import com.haeyum.common.domain.usecase.saved.ClearSavedTranslatesUseCase

class ClearDataUseCase(
    private val clearRecentTranslatesUseCase: ClearRecentTranslatesUseCase,
    private val clearSavedTranslatesUseCase: ClearSavedTranslatesUseCase
) {
    suspend operator fun invoke() {
        clearRecentTranslatesUseCase()
        clearSavedTranslatesUseCase()
    }
}