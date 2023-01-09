package presentation.window.translation.section.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.window.translation.Command
import presentation.window.translation.TranslationViewModel

@Composable
fun TranslateSearchSection(
    query: String,
    viewModel: TranslationViewModel,
    focusRequester: FocusRequester,
    commandInference: Command?
) {
    Row(
        modifier = Modifier.fillMaxWidth().height(57.dp).padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = query,
            onValueChange = {
                viewModel.setQuery(it)
            },
            modifier = Modifier.weight(1f)
                .focusRequester(focusRequester)
                .onPreviewKeyEvent(viewModel::onPreviewKeyEvent),
            textStyle = TextStyle(color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Medium),
            singleLine = true,
            maxLines = 1,
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    innerTextField()
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    if (query.isEmpty()) {
                        Text(
                            text = "Enter text to translate...",
                            style = TextStyle(
                                color = Color(0xFF999999),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    } else {
                        commandInference?.query?.let { command ->
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(Color.Black)) {
                                        append(query)
                                    }
                                    append(command.lowercase().removePrefix(query.lowercase()))
                                },
                                style = TextStyle(
                                    color = Color(0xFF999999),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
            }
        )
    }
}