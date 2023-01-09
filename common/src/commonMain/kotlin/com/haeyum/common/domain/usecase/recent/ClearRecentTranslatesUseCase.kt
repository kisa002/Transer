package com.haeyum.common.domain.usecase.recent

import com.haeyum.common.domain.repository.RecentTranslateRepository

class ClearRecentTranslatesUseCase(
    private val recentTranslateRepository: RecentTranslateRepository
) {
    suspend operator fun invoke() = recentTranslateRepository.clearRecentTranslates()
}