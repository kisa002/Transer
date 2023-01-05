package com.haeyum.common.domain.usecase

import com.haeyum.common.domain.model.translation.preferences.Preferences
import com.haeyum.common.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetPreferencesUseCase(private val preferencesRepository: PreferencesRepository) {
    suspend operator fun invoke(): Flow<Preferences?> = preferencesRepository.getPreferences()
}