package com.haeyum.common.data.repository

import com.haeyum.common.data.mapper.toDomain
import com.haeyum.common.data.repository.translation.TranslationDataSource
import com.haeyum.common.domain.model.translation.detect.Detection
import com.haeyum.common.domain.model.translation.languages.Language
import com.haeyum.common.domain.model.translation.translate.Translation
import com.haeyum.common.domain.repository.TranslationRepository

class TranslationRepositoryImpl(private val translationDataSource: TranslationDataSource) : TranslationRepository {
    override suspend fun translate(q: String, target: String, source: String, key: String): Translation =
        translationDataSource.translate(q = q, target = target, source = source, key = key).toDomain()

    override suspend fun detectLanguage(q: String, key: String): Detection =
        translationDataSource.detectLanguage(q = q, key = key).toDomain()

    override suspend fun getLanguages(target: String, key: String): List<Language> =
        translationDataSource.getLanguages(target = target, key = key).toDomain()
}