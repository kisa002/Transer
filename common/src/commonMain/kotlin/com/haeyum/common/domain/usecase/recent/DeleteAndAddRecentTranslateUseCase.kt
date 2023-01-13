package com.haeyum.common.domain.usecase.recent

class DeleteAndAddRecentTranslateUseCase(
    private val deleteRecentTranslateByTranslatedTextUseCase: DeleteRecentTranslateByTranslatedTextUseCase,
    private val addRecentTranslateUseCase: AddRecentTranslateUseCase
) {
    suspend operator fun invoke(
        originalText: String,
        translatedText: String
    ) {
        deleteRecentTranslateByTranslatedTextUseCase(translatedText)
        addRecentTranslateUseCase(originalText, translatedText)
    }
}