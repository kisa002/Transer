package com.haeyum.shared.domain.usecase

import com.haeyum.shared.domain.usecase.recent.ClearRecentTranslatesUseCase
import com.haeyum.shared.domain.usecase.saved.ClearSavedTranslatesUseCase

class ClearDataUseCase(
    private val clearRecentTranslatesUseCase: ClearRecentTranslatesUseCase,
    private val clearSavedTranslatesUseCase: ClearSavedTranslatesUseCase
) {
    suspend operator fun invoke() {
        clearRecentTranslatesUseCase()
        clearSavedTranslatesUseCase()
    }
}