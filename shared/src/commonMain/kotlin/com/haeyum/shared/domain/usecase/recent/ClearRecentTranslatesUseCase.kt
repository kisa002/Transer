package com.haeyum.shared.domain.usecase.recent

import com.haeyum.shared.domain.repository.RecentTranslateRepository

class ClearRecentTranslatesUseCase(
    private val recentTranslateRepository: RecentTranslateRepository
) {
    suspend operator fun invoke() = recentTranslateRepository.clearRecentTranslates()
}