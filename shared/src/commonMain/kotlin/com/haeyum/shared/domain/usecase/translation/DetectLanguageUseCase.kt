package com.haeyum.shared.domain.usecase.translation

import com.haeyum.shared.domain.repository.TranslationRepository

class DetectLanguageUseCase(private val translationRepository: TranslationRepository) {
    suspend operator fun invoke(q: String) =
        translationRepository.detectLanguage(q = q)
}