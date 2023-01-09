package presentation.window.translation.section.sub

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.component.SectionHeader
import presentation.window.translation.TranslationScreenErrorEvent
import presentation.window.translation.TranslationScreenState

@Composable
fun ErrorSection(translationScreenState: TranslationScreenState) {
    Column(modifier = Modifier.fillMaxSize()) {
        when ((translationScreenState as TranslationScreenState.Error).errorEvent) {
            TranslationScreenErrorEvent.DisconnectedNetwork -> ErrorInfo(
                title = "Disconnected Network",
                description = "Please check your network connection."
            )

            TranslationScreenErrorEvent.FailedTranslate -> ErrorInfo(
                title = "Failed Translate",
                description = "Please change the text or try again later."
            )

            TranslationScreenErrorEvent.NotFoundPreferences -> ErrorInfo(
                title = "Not found preferences",
                description = "Please reinstall the app."
            )

            TranslationScreenErrorEvent.WrongCommand -> ErrorInfo(
                title = "Wrong command",
                description = "Please check the command."
            )
        }
    }
}

@Composable
private fun ErrorInfo(title: String, description: String) {
    SectionHeader(text = title, textColor = Color(0xFFE60000))
    Text(
        text = description,
        modifier = Modifier.padding(top = 12.dp).padding(horizontal = 18.dp),
        style = TextStyle(color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Normal)
    )
}