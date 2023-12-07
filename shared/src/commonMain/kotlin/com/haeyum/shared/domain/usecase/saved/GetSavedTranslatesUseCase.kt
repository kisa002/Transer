package com.haeyum.shared.domain.usecase.saved

import com.haeyum.shared.domain.repository.SavedTranslateRepository

class GetSavedTranslatesUseCase(private val savedTranslateRepository: SavedTranslateRepository) {
    operator fun invoke() = savedTranslateRepository.getSavedTranslateList()
}