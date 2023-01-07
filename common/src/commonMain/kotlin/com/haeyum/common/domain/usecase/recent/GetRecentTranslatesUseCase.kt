package com.haeyum.common.domain.usecase.recent

import com.haeyum.common.domain.repository.RecentTranslateRepository

class GetRecentTranslatesUseCase(private val recentTranslateRepository: RecentTranslateRepository) {
    suspend operator fun invoke() = recentTranslateRepository.getRecentTranslates()
}