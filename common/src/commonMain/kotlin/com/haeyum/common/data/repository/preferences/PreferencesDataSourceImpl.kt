package com.haeyum.common.data.repository.preferences

import com.haeyum.common.TranserDatabase
import com.haeyum.common.data.model.preferences.Preferences
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesDataSourceImpl(private val database: TranserDatabase) : PreferencesDataSource {
    override suspend fun getPreferences(): Flow<Preferences?> =
        database.preferencesQueries.select().asFlow().mapToOneOrNull().map {
            it?.let {
                Preferences(it.nativeLanguage, it.targetLanguage)
            }
        }

    override suspend fun insertPreferences(nativeLanguage: String, targetLanguage: String) {
        database.preferencesQueries.set(
            nativeLanguage = nativeLanguage,
            targetLanguage = targetLanguage
        )
    }
}