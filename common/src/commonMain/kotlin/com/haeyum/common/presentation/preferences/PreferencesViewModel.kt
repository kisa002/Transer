package com.haeyum.common.presentation.preferences

import com.haeyum.common.domain.usecase.GetPreferencesUseCase
import com.haeyum.common.domain.usecase.SetPreferencesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PreferencesViewModel(
    private val ioScope: CoroutineScope,
    private val getPreferencesUseCase: GetPreferencesUseCase,
    private val setPreferencesUseCase: SetPreferencesUseCase
) {
    val preferences = channelFlow {
        getPreferencesUseCase().collectLatest { preferences ->
            send(preferences)
        }
    }.shareIn(ioScope, SharingStarted.Eagerly, 1)

    val selectedNativeLanguage = preferences.map {
        it?.nativeLanguage ?: "-"
    }.stateIn(scope = ioScope, started = SharingStarted.Eagerly, "-")

    val selectedTargetLanguage = preferences.map {
        it?.targetLanguage ?: "-"
    }.stateIn(scope = ioScope, started = SharingStarted.Eagerly, "-")

    fun setSelectedNativeLanguage(language: String) {
        ioScope.launch {
            setPreferencesUseCase(nativeLanguage = language, targetLanguage = selectedTargetLanguage.value)
        }
    }

    fun setSelectedTargetLanguage(language: String) {
        ioScope.launch {
            setPreferencesUseCase(nativeLanguage = selectedNativeLanguage.value, targetLanguage = language)
        }
    }

    fun onDestroy() {
        ioScope.cancel()
    }

    val testLanguageDataset = listOf(
        "Afrikaans",
        "Akan",
        "Albanian",
        "Amharic",
        "Arabic",
        "Armenian",
        "Assamese",
        "Aymara",
        "Azerbaijani",
        "Bambara",
        "Basque",
        "Belarusian",
        "Bengali",
        "Bhojpuri",
        "Bosnian",
        "Bulgarian",
        "Catalan",
        "Cebuano",
        "Chichewa",
        "Chinese (Simplified)",
        "Chinese (Traditional)",
        "Corsican",
        "Croatian",
        "Czech",
        "Danish",
        "Divehi",
        "Dogri",
        "Dutch",
        "English",
        "Esperanto",
        "Estonian",
        "Ewe",
        "Filipino",
        "Finnish",
        "French",
        "Frisian",
        "Galician",
        "Ganda",
        "Georgian",
        "German",
        "Goan Konkani",
        "Greek",
        "Guarani",
        "Gujarati",
        "Haitian Creole",
        "Hausa",
        "Hawaiian",
        "Hebrew",
        "Hindi",
        "Hmong",


        "Hungarian",


        "Icelandic",


        "Igbo",


        "Iloko",


        "Indonesian",


        "Irish",


        "Italian",


        "Japanese",


        "Javanese",


        "Kannada",


        "Kazakh",


        "Khmer",


        "Kinyarwanda",


        "Korean",


        "Krio",


        "Kurdish (Kurmanji)",


        "Kurdish (Sorani)",


        "Kyrgyz",


        "Lao",


        "Latin",


        "Latvian",


        "Lingala",


        "Lithuanian",


        "Luxembourgish",


        "Macedonian",


        "Maithili",


        "Malagasy",


        "Malay",


        "Malayalam",


        "Maltese",


        "Manipuri (Meitei Mayek)",


        "Maori",


        "Marathi",


        "Mizo",


        "Mongolian",


        "Myanmar (Burmese)",


        "Nepali",


        "Northern Sotho",


        "Norwegian",


        "Odia (Oriya)",


        "Oromo",


        "Pashto",


        "Persian",


        "Polish",


        "Portuguese",


        "Punjabi",


        "Quechua",


        "Romanian",


        "Russian",


        "Samoan",


        "Sanskrit",


        "Scots Gaelic",


        "Serbian",


        "Sesotho",


        "Shona",


        "Sindhi",


        "Sinhala",


        "Slovak",


        "Slovenian",


        "Somali",


        "Spanish",


        "Sundanese",


        "Swahili",


        "Swedish",


        "Tajik",


        "Tamil",


        "Tatar",


        "Telugu",


        "Thai",


        "Tigrinya",


        "Tsonga",


        "Turkish",


        "Turkmen",


        "Ukrainian",


        "Urdu",


        "Uyghur",


        "Uzbek",


        "Vietnamese",


        "Welsh",


        "Xhosa",


        "Yiddish",


        "Yoruba",


        "Zulu",


        "Hebrew",


        "Javanese",


        "Chinese (Simplified)"
    )
}