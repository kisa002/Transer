package com.haeyum.common.data.repository.saved

import com.haeyum.common.TranserDatabase
import com.haeyum.common.data.model.saved.SavedTranslate
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SavedTranslateDataSourceImpl(private val database: TranserDatabase) : SavedTranslateDataSource {
    override suspend fun selectSavedTranslate(translatedText: String): Flow<SavedTranslate?> =
        database.savedTranslateQueries.select(translatedText).asFlow().mapToOneOrNull().map { savedTranslate ->
            savedTranslate?.let {
                SavedTranslate(
                    idx = savedTranslate.idx.toInt(),
                    originalText = savedTranslate.originalText,
                    translatedText = savedTranslate.translatedText
                )
            }
        }

    override suspend fun selectAllSavedTranslateList(): Flow<List<SavedTranslate>> =
        database.savedTranslateQueries.selectAll().asFlow().mapToList().map {
            it.map { savedTranslate ->
                SavedTranslate(
                    idx = savedTranslate.idx.toInt(),
                    originalText = savedTranslate.originalText,
                    translatedText = savedTranslate.translatedText
                )
            }
        }

    override suspend fun insertSavedTranslate(originalText: String, translatedText: String) =
        database.savedTranslateQueries.insert(originalText, translatedText)

    override suspend fun deleteSavedTranslateByIdx(idx: Int) = database.savedTranslateQueries.deleteByIdx(idx.toLong())
    override suspend fun deleteSavedTranslateByTranslatedText(translatedText: String) =
        database.savedTranslateQueries.deleteByTranslatedText(translatedText)

    override suspend fun deleteAllSavedTranslates() = database.savedTranslateQueries.deleteAll()
}