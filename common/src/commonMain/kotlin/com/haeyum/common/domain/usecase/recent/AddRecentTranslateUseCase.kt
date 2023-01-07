package com.haeyum.common.domain.usecase.recent

import com.haeyum.common.domain.model.translation.languages.Language
import com.haeyum.common.domain.repository.RecentTranslateRepository

class AddRecentTranslateUseCase(private val recentTranslateRepository: RecentTranslateRepository) {
    suspend operator fun invoke(
        originalText: String,
        translatedText: String,
        sourceLanguage: Language,
        targetLanguage: Language
    ) = recentTranslateRepository.addRecentTranslate(
        originalText = originalText,
        translatedText = translatedText,
        sourceLanguage = sourceLanguage,
        targetLanguage = targetLanguage
    )
}