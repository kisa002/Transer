package com.haeyum.common.domain.usecase.recent

import com.haeyum.common.domain.repository.RecentTranslateRepository

class DeleteRecentTranslateUseCase(private val recentTranslateRepository: RecentTranslateRepository) {
    suspend operator fun invoke(idx: Int) = recentTranslateRepository.deleteRecentTranslate(idx)
}