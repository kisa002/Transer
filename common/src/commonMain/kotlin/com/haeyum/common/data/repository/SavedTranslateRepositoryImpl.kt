package com.haeyum.common.data.repository

import com.haeyum.common.data.mapper.toDomain
import com.haeyum.common.data.model.saved.SavedTranslate
import com.haeyum.common.data.repository.saved.SavedTranslateDataSource
import com.haeyum.common.domain.repository.SavedTranslateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SavedTranslateRepositoryImpl(
    private val savedTranslateDataSource: SavedTranslateDataSource
) : SavedTranslateRepository {
    override suspend fun isExists(translatedText: String): Flow<Boolean> =
        savedTranslateDataSource.selectSavedTranslate(translatedText).map {
            it != null
        }

    override suspend fun getSavedTranslateList(): Flow<List<com.haeyum.common.domain.model.saved.SavedTranslate>> =
        savedTranslateDataSource.selectAllSavedTranslateList().map {
            it.map(SavedTranslate::toDomain)
        }

    override suspend fun addSavedTranslate(originalText: String, translatedText: String) =
        savedTranslateDataSource.insertSavedTranslate(originalText, translatedText)

    override suspend fun deleteSavedTranslateByIdx(idx: Int) = savedTranslateDataSource.deleteSavedTranslateByIdx(idx)

    override suspend fun deleteSavedTranslateByTranslatedText(translatedText: String) =
        savedTranslateDataSource.deleteSavedTranslateByTranslatedText(translatedText)
}