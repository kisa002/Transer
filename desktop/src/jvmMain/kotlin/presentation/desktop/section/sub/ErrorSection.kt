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
import presentation.desktop.DesktopScreenErrorEvent
import presentation.desktop.DesktopScreenState

@Composable
fun ErrorSection(desktopScreenState: DesktopScreenState) {
    Column(modifier = Modifier.fillMaxSize()) {
        when ((desktopScreenState as DesktopScreenState.Error).errorEvent) {
            DesktopScreenErrorEvent.DisconnectedNetwork -> {
                Text(
                    text = "Disconnected Network",
                    modifier = Modifier.padding(top = 12.dp).padding(horizontal = 18.dp),
                    style = TextStyle(
                        color = Color(0xFFE60000),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = "Please check your network connection.",
                    modifier = Modifier.padding(top = 12.dp).padding(horizontal = 18.dp),
                    style = TextStyle(color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Normal)
                )
            }

            DesktopScreenErrorEvent.FailedTranslate -> {
                Text(
                    text = "Failed Translate",
                    modifier = Modifier.padding(top = 12.dp).padding(horizontal = 18.dp),
                    style = TextStyle(
                        color = Color(0xFFE60000),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = "Translation failed. Please change the text or try again later.",
                    modifier = Modifier.padding(top = 12.dp).padding(horizontal = 18.dp),
                    style = TextStyle(color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Normal)
                )
            }

            DesktopScreenErrorEvent.NotFoundPreferences -> {
                Text(
                    text = "Not found preferences",
                    modifier = Modifier.padding(top = 12.dp).padding(horizontal = 18.dp),
                    style = TextStyle(
                        color = Color(0xFFE60000),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = "Preferences file not found. Please reinstall the app.",
                    modifier = Modifier.padding(top = 12.dp).padding(horizontal = 18.dp),
                    style = TextStyle(color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Normal)
                )
            }

            DesktopScreenErrorEvent.WrongCommand -> {
                Text(
                    text = "Wrong command",
                    modifier = Modifier.padding(top = 12.dp).padding(horizontal = 18.dp),
                    style = TextStyle(
                        color = Color(0xFFE60000),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = "Please check the command.",
                    modifier = Modifier.padding(top = 12.dp).padding(horizontal = 18.dp),
                    style = TextStyle(color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Normal)
                )
            }
        }
    }
}