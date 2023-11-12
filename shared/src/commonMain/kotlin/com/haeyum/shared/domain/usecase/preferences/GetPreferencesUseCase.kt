package com.haeyum.shared.domain.usecase.preferences

import com.haeyum.shared.domain.model.translation.preferences.Preferences
import com.haeyum.shared.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetPreferencesUseCase(private val preferencesRepository: PreferencesRepository) {
    suspend operator fun invoke(): Flow<Preferences?> = preferencesRepository.getPreferences()
}