package com.haeyum.shared.domain.usecase.saved

import com.haeyum.shared.domain.repository.SavedTranslateRepository

class IsExistsSavedTranslateUseCase(private val savedTranslateRepository: SavedTranslateRepository) {
    suspend operator fun invoke(translatedText: String) = savedTranslateRepository.isExists(translatedText)
}