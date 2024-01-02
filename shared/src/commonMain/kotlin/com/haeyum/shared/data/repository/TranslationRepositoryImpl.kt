package com.haeyum.shared.data.repository

import com.haeyum.shared.data.mapper.toDomain
import com.haeyum.shared.data.repository.translation.TranslationDataSource
import com.haeyum.shared.domain.model.translation.detect.Detection
import com.haeyum.shared.domain.model.translation.languages.Language
import com.haeyum.shared.domain.model.translation.translate.Translation
import com.haeyum.shared.domain.repository.TranslationRepository

class TranslationRepositoryImpl(private val translationDataSource: TranslationDataSource) : TranslationRepository {
    override suspend fun translate(q: String, target: String, source: String): Translation =
        translationDataSource.translate(q = q, target = target, source = source).toDomain()

    override suspend fun detectLanguage(q: String): Detection =
        translationDataSource.detectLanguage(q = q).toDomain()

    override suspend fun getLanguages(target: String): List<Language> =
        translationDataSource.getLanguages(target = target).toDomain()
}