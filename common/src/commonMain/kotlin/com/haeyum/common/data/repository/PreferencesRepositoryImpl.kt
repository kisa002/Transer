package com.haeyum.common.data.repository

import com.haeyum.common.data.mapper.toDomain
import com.haeyum.common.data.repository.preferences.PreferencesDataSource
import com.haeyum.common.domain.model.translation.languages.Language
import com.haeyum.common.domain.model.translation.preferences.Preferences
import com.haeyum.common.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesRepositoryImpl(private val preferencesDataSource: PreferencesDataSource) : PreferencesRepository {
    override suspend fun getPreferences(): Flow<Preferences?> =
        preferencesDataSource.getPreferences().map { it?.toDomain() }

    override suspend fun setPreferences(sourceLanguage: Language, targetLanguage: Language) =
        preferencesDataSource.insertPreferences(
            sourceLanguage = com.haeyum.common.data.model.languages.Language(
                sourceLanguage.language,
                sourceLanguage.name
            ),
            targetLanguage = com.haeyum.common.data.model.languages.Language(
                targetLanguage.language,
                targetLanguage.name
            )
        )
}