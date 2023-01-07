package com.haeyum.common.domain.repository

import com.haeyum.common.domain.model.saved.SavedTranslate
import kotlinx.coroutines.flow.Flow

interface SavedTranslateRepository {
    suspend fun getSavedTranslateList(): Flow<List<SavedTranslate>>
    suspend fun addSavedTranslate(originalText: String, translatedText: String)
    suspend fun deleteSavedTranslate(idx: Int)
}