package com.haeyum.common.presentation

import com.haeyum.common.domain.usecase.GetSupportedLanguagesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

class PreferencesViewModel(ioScope: CoroutineScope, languagesUseCase: GetSupportedLanguagesUseCase) {
//    val languages = flow {
//        emit(languagesUseCase(target = "en", key = ""))
//    }.map {
//        it.map {
//            it.name
//        }
//    }.catch {
//        emit(emptyList())
//    }.stateIn(scope = ioScope, started = SharingStarted.Eagerly, emptyList())

    private val _selectedNativeLanguage = MutableStateFlow("")
    val selectedNativeLanguage: StateFlow<String> = _selectedNativeLanguage

    private val _selectedTargetLanguage = MutableStateFlow("")
    val selectedTargetLanguage: StateFlow<String> = _selectedTargetLanguage

    fun setSelectedNativeLanguage(language: String) {
        _selectedNativeLanguage.value = language
    }

    fun setSelectedTargetLanguage(language: String) {
        _selectedTargetLanguage.value = language
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