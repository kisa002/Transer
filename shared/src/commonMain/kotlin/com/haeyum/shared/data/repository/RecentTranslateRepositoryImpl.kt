package com.haeyum.shared.data.repository

import com.haeyum.shared.data.mapper.toDomain
import com.haeyum.shared.data.repository.recent.RecentTranslateDataSource
import com.haeyum.shared.domain.model.recent.RecentTranslate
import com.haeyum.shared.domain.repository.RecentTranslateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecentTranslateRepositoryImpl(
    private val recentTranslateDataSource: RecentTranslateDataSource
) : RecentTranslateRepository {
    override suspend fun getRecentTranslates(): Flow<List<RecentTranslate>> =
        recentTranslateDataSource.getRecentTranslates()
            .map(List<com.haeyum.shared.data.model.recent.RecentTranslate>::toDomain)

    override suspend fun addRecentTranslate(
        originalText: String,
        translatedText: String
    ) {
        recentTranslateDataSource.addRecentTranslate(
            originalText = originalText,
            translatedText = translatedText
        )
    }

    override suspend fun deleteRecentTranslateByIdx(idx: Int) = recentTranslateDataSource.deleteRecentTranslateById(idx)

    override suspend fun deleteRecentTranslateByTranslatedText(translatedText: String) =
        recentTranslateDataSource.deleteRecentTranslateByTranslatedText(translatedText)

    override suspend fun clearRecentTranslates() = recentTranslateDataSource.deleteAllRecentTranslates()
}