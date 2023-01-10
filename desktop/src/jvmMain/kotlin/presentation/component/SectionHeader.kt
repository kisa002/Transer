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

@Composable
fun SectionHeader(text: String, textColor: Color = Color(0xFF3F8CFF)) {
    Text(
        text = text,
        modifier = Modifier.padding(top = 12.dp).padding(horizontal = 18.dp),
        style = TextStyle(color = textColor, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    )
}