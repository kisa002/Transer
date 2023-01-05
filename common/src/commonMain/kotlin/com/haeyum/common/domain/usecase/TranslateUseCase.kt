package com.haeyum.common.domain.usecase

import com.haeyum.common.domain.repository.TranslationRepository
import kotlinx.coroutines.flow.firstOrNull

class TranslateUseCase(
    private val translationRepository: TranslationRepository,
    private val detectLanguageUseCase: DetectLanguageUseCase,
    private val getPreferencesUseCase: GetPreferencesUseCase
) {
    suspend operator fun invoke(q: String, key: String) =
        detectLanguageUseCase(q, key).language.let { language ->
            val (source, target) = getPreferencesUseCase().firstOrNull()
                ?: throw NullPointerException("Preferences is null")

            makeSourceTargetPair(language, target.language, source.language).let { (target, source) ->
                translationRepository.translate(q = q, target = target, source = source, key = key)
            }
        }

    /** If the input text is the same as the language to be translated or that language does not match the source language, the request format is changed for normal translation. **/
    private fun makeSourceTargetPair(language: String, target: String, source: String): Pair<String, String> =
        when {
            language == "und" -> Pair(target, source)
            language == target -> source to target
            language != source -> target to language
            else -> target to source
        }
}