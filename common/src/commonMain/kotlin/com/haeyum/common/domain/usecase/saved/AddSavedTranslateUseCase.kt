package com.haeyum.common.domain.usecase.saved

import com.haeyum.common.domain.repository.SavedTranslateRepository

class AddSavedTranslateUseCase(private val savedTranslateRepository: SavedTranslateRepository) {
    suspend operator fun invoke(originalText: String, translatedText: String) =
        savedTranslateRepository.addSavedTranslate(originalText, translatedText)
}