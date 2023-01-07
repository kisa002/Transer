package com.haeyum.common.domain.repository

import com.haeyum.common.domain.model.recent.RecentTranslate
import com.haeyum.common.domain.model.translation.languages.Language
import kotlinx.coroutines.flow.Flow

interface RecentTranslateRepository {
    suspend fun getRecentTranslates(): Flow<List<RecentTranslate>>
    suspend fun addRecentTranslate(
        originalText: String,
        translatedText: String,
        sourceLanguage: Language,
        targetLanguage: Language
    )
    suspend fun deleteRecentTranslate(idx: Int)
}