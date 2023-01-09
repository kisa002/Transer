package presentation.translation.section.sub

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haeyum.common.domain.model.saved.SavedTranslate
import presentation.component.SectionHeader
import presentation.component.TranslatedItem

@Composable
fun SavedSection(
    savedTranslates: List<SavedTranslate>,
    currentSelectedIndex: Int,
    listState: LazyListState,
    isExistsSavedTranslate: Boolean,
    onClickTranslatedItem: (String, String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SectionHeader("Saved")

        LazyColumn(modifier = Modifier.fillMaxSize().padding(12.dp), state = listState) {
            itemsIndexed(savedTranslates) { index, savedTranslate ->
                TranslatedItem(
                    originalText = savedTranslate.originalText,
                    translatedText = savedTranslate.translatedText,
                    isSelected = index == currentSelectedIndex,
                    isExists = isExistsSavedTranslate,
                    onClick = onClickTranslatedItem
                )
            }
        }
    }
}