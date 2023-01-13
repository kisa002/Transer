package com.haeyum.common.domain.usecase.translation

import com.haeyum.common.domain.repository.TranslationRepository

class GetSupportedLanguagesUseCase(private val translationRepository: TranslationRepository) {
    suspend operator fun invoke(target: String) =
        translationRepository.getLanguages(target = target)
}