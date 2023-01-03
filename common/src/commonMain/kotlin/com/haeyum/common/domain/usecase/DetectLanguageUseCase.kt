package com.haeyum.common.domain.usecase

import com.haeyum.common.domain.repository.TranslationRepository

class DetectLanguageUseCase(private val translationRepository: TranslationRepository) {
    suspend operator fun invoke(q: String, key: String) =
        translationRepository.detectLanguage(q = q, key = key)
}