package presentation.window.translation.section.sub

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haeyum.common.presentation.theme.ColorText
import presentation.component.SectionHeader

@Composable
fun HomeSection() {
    Column(modifier = Modifier.fillMaxSize()) {
        SectionHeader("Guide")
        Text(
            text = buildAnnotatedString {
                append("You can execute by typing ")
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(">")
                }
                append(" and entering the command.\n\n")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(">recent\n")
                }
                append("Shows recent translation results.\n\n")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(">saved\n")
                }
                append("Shows the saved translation results.\n\n")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(">preferences\n")
                }
                append("Show the Application Settings, including selecting Translation Language.\n")
            },
            modifier = Modifier.padding(top = 12.dp).padding(horizontal = 18.dp),
            style = TextStyle(color = ColorText, fontSize = 14.sp, fontWeight = FontWeight.Normal)
        )
    }
}