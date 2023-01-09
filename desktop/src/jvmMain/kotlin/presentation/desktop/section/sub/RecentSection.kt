package presentation.desktop.section.sub

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haeyum.common.domain.model.recent.RecentTranslate
import presentation.component.TranslatedItem

@Composable
fun RecentSection(
    recentTranslates: List<RecentTranslate>,
    currentSelectedIndex: Int,
    listState: LazyListState,
    isExistsSavedTranslate: Boolean,
    onClickTranslatedItem: (String, String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Recent",
            modifier = Modifier.padding(top = 12.dp).padding(horizontal = 18.dp),
            style = TextStyle(color = Color(0xFF3F8CFF), fontSize = 16.sp, fontWeight = FontWeight.Medium)
        )

        LazyColumn(modifier = Modifier.fillMaxSize().padding(12.dp), state = listState) {
            itemsIndexed(recentTranslates) { index, recentTranslate ->
                TranslatedItem(
                    originalText = recentTranslate.originalText,
                    translatedText = recentTranslate.translatedText,
                    isSelected = index == currentSelectedIndex,
                    isExists = isExistsSavedTranslate,
                    onClick = onClickTranslatedItem
                )
            }
        }
    }
}