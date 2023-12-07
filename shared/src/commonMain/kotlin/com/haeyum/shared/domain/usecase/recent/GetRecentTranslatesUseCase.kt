package com.haeyum.shared.domain.usecase.recent

import com.haeyum.shared.domain.repository.RecentTranslateRepository

class GetRecentTranslatesUseCase(private val recentTranslateRepository: RecentTranslateRepository) {
    operator fun invoke() = recentTranslateRepository.getRecentTranslates()
}