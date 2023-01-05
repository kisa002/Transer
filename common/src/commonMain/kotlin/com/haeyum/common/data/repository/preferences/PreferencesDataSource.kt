package com.haeyum.common.data.repository.preferences

import com.haeyum.common.data.model.languages.Language
import com.haeyum.common.data.model.preferences.Preferences
import kotlinx.coroutines.flow.Flow

interface PreferencesDataSource {
    suspend fun getPreferences(): Flow<Preferences?>
    suspend fun insertPreferences(sourceLanguage: Language, targetLanguage: Language)
}