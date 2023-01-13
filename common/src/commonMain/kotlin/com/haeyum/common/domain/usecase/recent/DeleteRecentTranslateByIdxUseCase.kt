package com.haeyum.common.domain.usecase.recent

import com.haeyum.common.domain.repository.RecentTranslateRepository

class DeleteRecentTranslateByIdxUseCase(private val recentTranslateRepository: RecentTranslateRepository) {
    suspend operator fun invoke(idx: Int) = recentTranslateRepository.deleteRecentTranslateByIdx(idx)
}