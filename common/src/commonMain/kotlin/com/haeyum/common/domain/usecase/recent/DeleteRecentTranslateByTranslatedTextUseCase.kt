package com.haeyum.common.domain.usecase.recent

import com.haeyum.common.domain.repository.RecentTranslateRepository

class DeleteRecentTranslateByTranslatedTextUseCase(private val recentTranslateRepository: RecentTranslateRepository) {
    suspend operator fun invoke(translatedText: String) =
        recentTranslateRepository.deleteRecentTranslateByTranslatedText(translatedText)
}