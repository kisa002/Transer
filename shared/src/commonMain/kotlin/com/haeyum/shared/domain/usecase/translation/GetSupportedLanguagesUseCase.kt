package com.haeyum.shared.domain.usecase.translation

import com.haeyum.shared.domain.repository.TranslationRepository

class GetSupportedLanguagesUseCase(private val translationRepository: TranslationRepository) {
    suspend operator fun invoke(target: String) =
        translationRepository.getLanguages(target = target)
}