package com.haeyum.common.data.repository.recent

import com.haeyum.common.data.model.recent.RecentTranslate
import kotlinx.coroutines.flow.Flow

interface RecentTranslateDataSource {
    suspend fun getRecentTranslates(): Flow<List<RecentTranslate>>
    suspend fun addRecentTranslate(originalText: String, translatedText: String)
    suspend fun deleteRecentTranslate(idx: Int)
}