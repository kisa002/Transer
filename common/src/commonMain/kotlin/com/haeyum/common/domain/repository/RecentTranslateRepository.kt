package com.haeyum.common.domain.repository

import com.haeyum.common.domain.model.recent.RecentTranslate
import kotlinx.coroutines.flow.Flow

interface RecentTranslateRepository {
    suspend fun getRecentTranslates(): Flow<List<RecentTranslate>>
    suspend fun addRecentTranslate(
        originalText: String,
        translatedText: String
    )
    suspend fun deleteRecentTranslateByIdx(idx: Int)
    suspend fun deleteRecentTranslateByTranslatedText(translatedText: String)
    suspend fun clearRecentTranslates()
}