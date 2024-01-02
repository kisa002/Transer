package com.haeyum.shared.domain.usecase.preferences

import com.haeyum.shared.domain.model.translation.languages.Language
import com.haeyum.shared.domain.repository.PreferencesRepository

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