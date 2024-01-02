package com.haeyum.shared.data.repository

import com.haeyum.shared.data.mapper.toDomain
import com.haeyum.shared.data.model.saved.SavedTranslate
import com.haeyum.shared.data.repository.saved.SavedTranslateDataSource
import com.haeyum.shared.domain.repository.SavedTranslateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SavedTranslateRepositoryImpl(
    private val savedTranslateDataSource: SavedTranslateDataSource
) : SavedTranslateRepository {
    override fun isExists(translatedText: String): Flow<Boolean> =
        savedTranslateDataSource.selectSavedTranslate(translatedText).map {
            it != null
        }

    override fun getSavedTranslateList(): Flow<List<com.haeyum.shared.domain.model.saved.SavedTranslate>> =
        savedTranslateDataSource.selectAllSavedTranslateList().map {
            it.map(SavedTranslate::toDomain)
        }

    override suspend fun addSavedTranslate(originalText: String, translatedText: String) =
        savedTranslateDataSource.insertSavedTranslate(originalText, translatedText)

    override suspend fun deleteSavedTranslateByIdx(idx: Int) = savedTranslateDataSource.deleteSavedTranslateByIdx(idx)

    override suspend fun deleteSavedTranslateByTranslatedText(translatedText: String) =
        savedTranslateDataSource.deleteSavedTranslateByTranslatedText(translatedText)

    override suspend fun clearSavedTranslates() = savedTranslateDataSource.deleteAllSavedTranslates()
}