package com.haeyum.shared.domain.repository

import com.haeyum.shared.domain.model.saved.SavedTranslate
import kotlinx.coroutines.flow.Flow

interface SavedTranslateRepository {
    fun isExists(translatedText: String): Flow<Boolean>
    fun getSavedTranslateList(): Flow<List<SavedTranslate>>
    suspend fun addSavedTranslate(originalText: String, translatedText: String)
    suspend fun deleteSavedTranslateByIdx(idx: Int)
    suspend fun deleteSavedTranslateByTranslatedText(translatedText: String)
    suspend fun clearSavedTranslates()
}