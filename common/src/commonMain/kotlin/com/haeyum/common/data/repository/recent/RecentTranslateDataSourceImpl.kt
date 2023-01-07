package com.haeyum.common.data.repository.recent

import com.haeyum.common.TranserDatabase
import com.haeyum.common.data.model.languages.Language
import com.haeyum.common.data.model.recent.RecentTranslate
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecentTranslateDataSourceImpl(private val database: TranserDatabase) : RecentTranslateDataSource {
    override suspend fun getRecentTranslates(): Flow<List<RecentTranslate>> =
        database.recentTranslateQueries.selectAll().asFlow().mapToList().map {
            it.map { recentTranslate ->
                RecentTranslate(
                    idx = recentTranslate.idx.toInt(),
                    originalText = recentTranslate.originalText,
                    translatedText = recentTranslate.translatedText,
                    sourceLanguage = Language(recentTranslate.sourceLanguage, recentTranslate.sourceName),
                    targetLanguage = Language(recentTranslate.targetLanguage, recentTranslate.targetName)
                )
            }
        }

    override suspend fun addRecentTranslate(
        originalText: String,
        translatedText: String,
        sourceLanguage: Language,
        targetLanguage: Language
    ) = database.recentTranslateQueries.insert(
        originalText = originalText,
        translatedText = translatedText,
        sourceLanguage = sourceLanguage.language,
        sourceName = sourceLanguage.name,
        targetLanguage = targetLanguage.language,
        targetName = targetLanguage.name
    )


    override suspend fun deleteRecentTranslate(idx: Int) = database.recentTranslateQueries.delete(idx.toLong())
}