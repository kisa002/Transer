package com.haeyum.common.data.repository.preferences

import com.haeyum.common.TranserDatabase
import com.haeyum.common.data.model.languages.Language
import com.haeyum.common.data.model.preferences.Preferences
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesDataSourceImpl(private val database: TranserDatabase) : PreferencesDataSource {
    override suspend fun getPreferences(): Flow<Preferences?> =
        database.preferencesQueries.select().asFlow().mapToOneOrNull().map {
            it?.let {
                Preferences(Language(it.sourceLanguage, it.sourceName), Language(it.targetLanguage, it.targetName))
            }
        }

    override suspend fun insertPreferences(sourceLanguage: Language, targetLanguage: Language) {
        database.preferencesQueries.set(
            sourceLanguage = sourceLanguage.language,
            sourceName = sourceLanguage.name,
            targetLanguage = targetLanguage.language,
            targetName = targetLanguage.name
        )
    }
}