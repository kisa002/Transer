package com.haeyum.shared.domain.usecase.saved

import com.haeyum.shared.domain.repository.SavedTranslateRepository

class DeleteSavedTranslateUseCase(private val savedTranslateRepository: SavedTranslateRepository) {
    suspend operator fun invoke(translatedText: String) = savedTranslateRepository.deleteSavedTranslateByTranslatedText(translatedText)
}