package com.haeyum.shared.data.repository.preferences

import com.haeyum.shared.data.model.languages.Language
import com.haeyum.shared.data.model.preferences.Preferences
import kotlinx.coroutines.flow.Flow

interface PreferencesDataSource {
    suspend fun getPreferences(): Flow<Preferences?>
    suspend fun insertPreferences(sourceLanguage: Language, targetLanguage: Language)
}