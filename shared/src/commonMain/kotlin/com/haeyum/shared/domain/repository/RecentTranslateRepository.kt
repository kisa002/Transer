package com.haeyum.shared.domain.repository

import com.haeyum.shared.domain.model.recent.RecentTranslate
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