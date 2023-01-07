package com.haeyum.common.data.repository.saved

import com.haeyum.common.data.model.saved.SavedTranslate
import kotlinx.coroutines.flow.Flow

interface SavedTranslateDataSource {
    suspend fun selectAllSavedTranslateList(): Flow<List<SavedTranslate>>
    suspend fun insertSavedTranslate(originalText: String, translatedText: String)
    suspend fun deleteSavedTranslate(idx: Int)
}