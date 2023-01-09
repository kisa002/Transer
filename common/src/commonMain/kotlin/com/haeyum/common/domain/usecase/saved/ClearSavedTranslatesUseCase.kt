package com.haeyum.common.domain.usecase.saved

import com.haeyum.common.domain.repository.SavedTranslateRepository

class ClearSavedTranslatesUseCase(
    private val savedTranslateRepository: SavedTranslateRepository
) {
    suspend operator fun invoke() = savedTranslateRepository.clearSavedTranslates()
}