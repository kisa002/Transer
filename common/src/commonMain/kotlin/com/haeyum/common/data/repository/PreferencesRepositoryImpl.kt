package com.haeyum.common.data.repository

import com.haeyum.common.data.mapper.toDomain
import com.haeyum.common.data.repository.preferences.PreferencesDataSource
import com.haeyum.common.domain.model.translation.preferences.Preferences
import com.haeyum.common.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesRepositoryImpl(private val preferencesDataSource: PreferencesDataSource) : PreferencesRepository {
    override suspend fun getPreferences(): Flow<Preferences?> =
        preferencesDataSource.getPreferences().map { it?.toDomain() }

    override suspend fun setPreferences(nativeLanguage: String, targetLanguage: String) =
        preferencesDataSource.insertPreferences(nativeLanguage = nativeLanguage, targetLanguage = targetLanguage)
}