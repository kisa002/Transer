package com.haeyum.shared.domain.usecase.saved

import com.haeyum.shared.domain.repository.SavedTranslateRepository

class AddSavedTranslateUseCase(private val savedTranslateRepository: SavedTranslateRepository) {
    suspend operator fun invoke(originalText: String, translatedText: String) =
        savedTranslateRepository.addSavedTranslate(originalText, translatedText)
}