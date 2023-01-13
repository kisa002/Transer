package com.haeyum.common.domain.repository

import com.haeyum.common.domain.model.translation.languages.Language
import com.haeyum.common.domain.model.translation.preferences.Preferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    suspend fun getPreferences(): Flow<Preferences?>
    suspend fun setPreferences(sourceLanguage: Language, targetLanguage: Language)
}