package presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haeyum.common.presentation.theme.Black
import com.haeyum.common.presentation.theme.ColorSelected
import com.haeyum.common.presentation.theme.ColorSelectedAction
import com.haeyum.common.presentation.theme.Transparent

@Composable
fun TranslatedItem(
    originalText: String,
    translatedText: String,
    isSelected: Boolean,
    isExists: Boolean,
    modifier: Modifier = Modifier,
    onClick: (String, String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .then(modifier)
            .clip(RoundedCornerShape(8.dp))
            .background(color = if (isSelected) ColorSelected else Transparent)
            .clickable {
                onClick(originalText, translatedText)
            }
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = translatedText,
            modifier = Modifier.weight(1f).padding(vertical = 6.dp),
            style = TextStyle(color = Black, fontSize = 14.sp, fontWeight = FontWeight.Normal)
        )
        if (isSelected) {
            Spacer(modifier = Modifier.size(24.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isExists) "⌥↵ Delete Saved" else "⌥↵ Save",
                    modifier = Modifier
                        .background(
                            color = ColorSelectedAction,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(6.dp),
                    color = Black,
                    fontSize = 12.sp
                )
                Text(
                    text = "↵ Copy",
                    modifier = Modifier
                        .background(
                            color = ColorSelectedAction,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(6.dp),
                    color = Black,
                    fontSize = 12.sp
                )
            }
        }
    }
}