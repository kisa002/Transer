package com.haeyum.common.domain.usecase

import com.haeyum.common.domain.model.translation.languages.Language
import com.haeyum.common.domain.repository.PreferencesRepository

class SetPreferencesUseCase(private val preferencesRepository: PreferencesRepository) {
    suspend operator fun invoke(
        sourceLanguage: Language,
        targetLanguage: Language,
    ) =
        preferencesRepository.setPreferences(
            sourceLanguage = sourceLanguage,
            targetLanguage = targetLanguage,
        )
}