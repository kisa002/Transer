package com.haeyum.common.domain.usecase.recent

import com.haeyum.common.domain.repository.RecentTranslateRepository

class AddRecentTranslateUseCase(private val recentTranslateRepository: RecentTranslateRepository) {
    suspend operator fun invoke(
        originalText: String,
        translatedText: String
    ) = recentTranslateRepository.addRecentTranslate(
        originalText = originalText,
        translatedText = translatedText
    )
}