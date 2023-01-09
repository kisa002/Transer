package presentation.desktop.section.sub

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
import presentation.desktop.DesktopScreenErrorEvent
import presentation.desktop.DesktopScreenState

@Composable
fun ErrorSection(desktopScreenState: DesktopScreenState) {
    Column(modifier = Modifier.fillMaxSize()) {
        when ((desktopScreenState as DesktopScreenState.Error).errorEvent) {
            DesktopScreenErrorEvent.DisconnectedNetwork -> ErrorInfo(
                title = "Disconnected Network",
                description = "Please check your network connection."
            )

            DesktopScreenErrorEvent.FailedTranslate -> ErrorInfo(
                title = "Failed Translate",
                description = "Please change the text or try again later."
            )

            DesktopScreenErrorEvent.NotFoundPreferences -> ErrorInfo(
                title = "Not found preferences",
                description = "Please reinstall the app."
            )

            DesktopScreenErrorEvent.WrongCommand -> ErrorInfo(
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