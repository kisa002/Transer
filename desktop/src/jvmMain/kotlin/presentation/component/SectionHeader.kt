package presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haeyum.shared.presentation.theme.ColorLightBlue

@Composable
fun SectionHeader(text: String, textColor: Color = ColorLightBlue) {
    Text(
        text = text,
        modifier = Modifier.padding(top = 12.dp).padding(horizontal = 18.dp),
        style = TextStyle(color = textColor, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    )
}