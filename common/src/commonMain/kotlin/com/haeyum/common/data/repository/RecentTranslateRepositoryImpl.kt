package com.haeyum.common.data.repository

import com.haeyum.common.data.mapper.toDomain
import com.haeyum.common.data.repository.recent.RecentTranslateDataSource
import com.haeyum.common.domain.model.recent.RecentTranslate
import com.haeyum.common.domain.model.translation.languages.Language
import com.haeyum.common.domain.repository.RecentTranslateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecentTranslateRepositoryImpl(
    private val recentTranslateDataSource: RecentTranslateDataSource
) : RecentTranslateRepository {
    override suspend fun getRecentTranslates(): Flow<List<RecentTranslate>> =
        recentTranslateDataSource.getRecentTranslates()
            .map(List<com.haeyum.common.data.model.recent.RecentTranslate>::toDomain)

    override suspend fun addRecentTranslate(
        originalText: String,
        translatedText: String,
        sourceLanguage: Language,
        targetLanguage: Language
    ) {
        recentTranslateDataSource.addRecentTranslate(
            originalText = originalText,
            translatedText = translatedText,
            sourceLanguage = com.haeyum.common.data.model.languages.Language(
                language = sourceLanguage.language,
                name = sourceLanguage.name
            ),
            targetLanguage = com.haeyum.common.data.model.languages.Language(
                language = targetLanguage.language,
                name = targetLanguage.name
            )
        )
    }

    override suspend fun deleteRecentTranslate(idx: Int) = recentTranslateDataSource.deleteRecentTranslate(idx)
}