package com.haeyum.common.data.repository

import com.haeyum.common.data.mapper.toDomain
import com.haeyum.common.data.repository.translation.TranslationDataSource
import com.haeyum.common.domain.model.translation.detect.Detection
import com.haeyum.common.domain.model.translation.languages.Language
import com.haeyum.common.domain.model.translation.translate.Translation
import com.haeyum.common.domain.repository.TranslationRepository

class TranslationRepositoryImpl(private val translationDataSource: TranslationDataSource) : TranslationRepository {
    override suspend fun translate(q: String, target: String, source: String): Translation =
        translationDataSource.translate(q = q, target = target, source = source).toDomain()

    override suspend fun detectLanguage(q: String): Detection =
        translationDataSource.detectLanguage(q = q).toDomain()

    override suspend fun getLanguages(target: String): List<Language> =
        translationDataSource.getLanguages(target = target).toDomain()
}