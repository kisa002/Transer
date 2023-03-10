package com.haeyum.common.domain.usecase.translation

import com.haeyum.common.domain.repository.TranslationRepository

class DetectLanguageUseCase(private val translationRepository: TranslationRepository) {
    suspend operator fun invoke(q: String) =
        translationRepository.detectLanguage(q = q)
}