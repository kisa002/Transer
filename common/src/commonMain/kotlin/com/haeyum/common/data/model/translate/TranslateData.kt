package com.haeyum.common.data.model.translate

import kotlinx.serialization.Serializable

@Serializable
data class TranslateData(
    val translations: List<Translation>
)
