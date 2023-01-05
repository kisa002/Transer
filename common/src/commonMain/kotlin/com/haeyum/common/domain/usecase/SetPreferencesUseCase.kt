package com.haeyum.common.domain.usecase

import com.haeyum.common.domain.model.translation.preferences.Preferences
import com.haeyum.common.domain.repository.PreferencesRepository

class SetPreferencesUseCase(private val preferencesRepository: PreferencesRepository) {
    suspend operator fun invoke(nativeLanguage: String, targetLanguage: String) =
        preferencesRepository.setPreferences(nativeLanguage, targetLanguage)
}