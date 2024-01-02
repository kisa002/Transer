package com.haeyum.shared.domain.usecase.recent

import com.haeyum.shared.domain.repository.RecentTranslateRepository

class AddRecentTranslateUseCase(private val recentTranslateRepository: RecentTranslateRepository) {
    suspend operator fun invoke(
        originalText: String,
        translatedText: String
    ) = recentTranslateRepository.addRecentTranslate(
        originalText = originalText,
        translatedText = translatedText
    )
}