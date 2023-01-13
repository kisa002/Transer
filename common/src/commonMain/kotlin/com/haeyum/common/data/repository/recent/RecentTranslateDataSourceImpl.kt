package com.haeyum.common.data.repository.recent

import com.haeyum.common.TranserDatabase
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
                    translatedText = recentTranslate.translatedText
                )
            }
        }

    override suspend fun addRecentTranslate(
        originalText: String,
        translatedText: String
    ) = database.recentTranslateQueries.insert(
        originalText = originalText,
        translatedText = translatedText
    )

    override suspend fun deleteRecentTranslateById(idx: Int) = database.recentTranslateQueries.deleteByIdx(idx.toLong())

    override suspend fun deleteRecentTranslateByTranslatedText(translatedText: String) =
        database.recentTranslateQueries.deleteByTranslatedText(translatedText)

    override suspend fun deleteAllRecentTranslates() = database.recentTranslateQueries.deleteAll()
}