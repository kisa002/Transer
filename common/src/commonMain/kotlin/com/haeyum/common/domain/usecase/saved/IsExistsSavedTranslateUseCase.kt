package com.haeyum.common.domain.usecase.saved

import com.haeyum.common.domain.repository.SavedTranslateRepository

class IsExistsSavedTranslateUseCase(private val savedTranslateRepository: SavedTranslateRepository) {
    suspend operator fun invoke(translatedText: String) = savedTranslateRepository.isExists(translatedText)
}