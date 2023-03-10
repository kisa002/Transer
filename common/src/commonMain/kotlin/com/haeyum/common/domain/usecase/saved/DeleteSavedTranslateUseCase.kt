package com.haeyum.common.domain.usecase.saved

import com.haeyum.common.domain.repository.SavedTranslateRepository

class DeleteSavedTranslateUseCase(private val savedTranslateRepository: SavedTranslateRepository) {
    suspend operator fun invoke(translatedText: String) = savedTranslateRepository.deleteSavedTranslateByTranslatedText(translatedText)
}