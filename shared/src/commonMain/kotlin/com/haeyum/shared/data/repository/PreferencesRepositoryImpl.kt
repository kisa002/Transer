package com.haeyum.shared.data.repository

import com.haeyum.shared.data.mapper.toDomain
import com.haeyum.shared.data.repository.preferences.PreferencesDataSource
import com.haeyum.shared.domain.model.translation.languages.Language
import com.haeyum.shared.domain.model.translation.preferences.Preferences
import com.haeyum.shared.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesRepositoryImpl(private val preferencesDataSource: PreferencesDataSource) : PreferencesRepository {
    override suspend fun getPreferences(): Flow<Preferences?> =
        preferencesDataSource.getPreferences().map { it?.toDomain() }

    override suspend fun setPreferences(sourceLanguage: Language, targetLanguage: Language) =
        preferencesDataSource.insertPreferences(
            sourceLanguage = com.haeyum.shared.data.model.languages.Language(
                sourceLanguage.language,
                sourceLanguage.name
            ),
            targetLanguage = com.haeyum.shared.data.model.languages.Language(
                targetLanguage.language,
                targetLanguage.name
            )
        )
}