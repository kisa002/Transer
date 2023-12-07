package com.haeyum.shared.data.repository.recent

import com.haeyum.shared.data.model.recent.RecentTranslate
import kotlinx.coroutines.flow.Flow

interface RecentTranslateDataSource {
    fun getRecentTranslates(): Flow<List<RecentTranslate>>
    suspend fun addRecentTranslate(originalText: String, translatedText: String)
    suspend fun deleteRecentTranslateById(idx: Int)
    suspend fun deleteRecentTranslateByTranslatedText(translatedText: String)
    suspend fun deleteAllRecentTranslates()
}