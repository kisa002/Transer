package com.haeyum.shared.domain.repository

import com.haeyum.shared.domain.model.translation.languages.Language
import com.haeyum.shared.domain.model.translation.preferences.Preferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun getPreferences(): Flow<Preferences?>
    suspend fun setPreferences(sourceLanguage: Language, targetLanguage: Language)
}