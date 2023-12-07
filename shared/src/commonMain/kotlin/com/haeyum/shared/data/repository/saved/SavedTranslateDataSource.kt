package com.haeyum.shared.data.repository.saved

import com.haeyum.shared.data.model.saved.SavedTranslate
import kotlinx.coroutines.flow.Flow

interface SavedTranslateDataSource {
    fun selectSavedTranslate(translatedText: String): Flow<SavedTranslate?>
    fun selectAllSavedTranslateList(): Flow<List<SavedTranslate>>
    suspend fun insertSavedTranslate(originalText: String, translatedText: String)
    suspend fun deleteSavedTranslateByIdx(idx: Int)
    suspend fun deleteSavedTranslateByTranslatedText(translatedText: String)
    suspend fun deleteAllSavedTranslates()
}