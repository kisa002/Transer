package com.haeyum.shared.domain.usecase.recent

import com.haeyum.shared.domain.repository.RecentTranslateRepository

class DeleteRecentTranslateByTranslatedTextUseCase(private val recentTranslateRepository: RecentTranslateRepository) {
    suspend operator fun invoke(translatedText: String) =
        recentTranslateRepository.deleteRecentTranslateByTranslatedText(translatedText)
}