package presentation.window.translation.section.sub

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haeyum.common.presentation.component.RainbowCircularProgressIndicator
import presentation.component.SectionHeader
import presentation.component.TranslatedItem

@Composable
fun ResultSection(
    query: String,
    translatedText: String,
    isRequesting: Boolean,
    isExistsSavedTranslate: Boolean,
    onClickTranslatedItem: (String, String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SectionHeader("Result")

        if (translatedText.isBlank() || isRequesting) {
            RainbowCircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 96.dp)
                    .size(64.dp)
            )
        } else {
            TranslatedItem(
                originalText = query,
                translatedText = translatedText,
                isSelected = true,
                isExists = isExistsSavedTranslate,
                modifier = Modifier.padding(12.dp),
                onClick = onClickTranslatedItem
            )
        }
    }
}