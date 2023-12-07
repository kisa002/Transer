package presentation.window.translation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.haeyum.shared.presentation.component.EventSnackBar
import com.haeyum.shared.presentation.component.rememberEventSnackbraState
import com.haeyum.shared.presentation.theme.ColorBackground
import com.haeyum.shared.presentation.theme.ColorDivider
import com.haeyum.shared.presentation.theme.ColorLightBlue
import kotlinx.coroutines.launch
import presentation.window.translation.section.search.TranslateSearchSection
import presentation.window.translation.section.sub.*

@Composable
fun TranslationScreen(viewModel: TranslationViewModel, alwaysOnTop: Boolean, onShowPreferences: () -> Unit = {}) {
    val query by viewModel.query.collectAsState()
    val translatedText by viewModel.translatedText.collectAsState()
    val isRequesting by viewModel.isRequesting.collectAsState()

    val desktopScreenState by viewModel.screenState.collectAsState()
    val commandInference by viewModel.commandInference.collectAsState()
    val recentTranslates by viewModel.recentTranslates.collectAsState()
    val savedTranslates by viewModel.savedTranslates.collectAsState()

    val isExistsSavedTranslate by viewModel.isExistsSavedTranslate.collectAsState()
    val currentSelectedIndex by viewModel.currentSelectedIndex.collectAsState()

    val eventSnackbarState = rememberEventSnackbraState()

    val focusRequester = remember { FocusRequester() }
    val clipboardManager = LocalClipboardManager.current

    val recentTranslateLazyListState = rememberLazyListState()
    val savedTranslatesLazyListState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorBackground, shape = RoundedCornerShape(8.dp))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TranslateSearchSection(
                query = query,
                viewModel = viewModel,
                focusRequester = focusRequester,
                commandInference = commandInference
            )
            Divider(modifier = Modifier, color = ColorDivider, thickness = (0.5).dp)
            when (desktopScreenState) {
                TranslationScreenState.Home -> HomeSection()

                TranslationScreenState.Recent -> RecentSection(
                    recentTranslates = recentTranslates,
                    currentSelectedIndex = currentSelectedIndex,
                    listState = recentTranslateLazyListState,
                    isExistsSavedTranslate = isExistsSavedTranslate,
                    onClickTranslatedItem = viewModel::onClickTranslatedItem
                )

                TranslationScreenState.Saved -> SavedSection(
                    savedTranslates = savedTranslates,
                    currentSelectedIndex = currentSelectedIndex,
                    listState = savedTranslatesLazyListState,
                    isExistsSavedTranslate = isExistsSavedTranslate,
                    onClickTranslatedItem = viewModel::onClickTranslatedItem
                )

                is TranslationScreenState.Error -> ErrorSection(desktopScreenState)

                else -> ResultSection(
                    query = query,
                    translatedText = translatedText,
                    isRequesting = isRequesting,
                    isExistsSavedTranslate = isExistsSavedTranslate,
                    onClickTranslatedItem = viewModel::onClickTranslatedItem
                )
            }
        }

        EventSnackBar(backgroundColor = ColorLightBlue, eventSnackbarState = eventSnackbarState)
    }

    LaunchedEffect(desktopScreenState, currentSelectedIndex) {
        when (desktopScreenState) {
            TranslationScreenState.Recent -> recentTranslateLazyListState.scrollToItem(currentSelectedIndex)
            TranslationScreenState.Saved -> savedTranslatesLazyListState.scrollToItem(currentSelectedIndex)
            else -> {
                /* no-op */
            }
        }
    }

    LaunchedEffect(query) {
        if (query.isEmpty()) {
            focusRequester.requestFocus()
        }
    }

    LaunchedEffect(alwaysOnTop) {
        if (alwaysOnTop) {
            focusRequester.requestFocus()
        }
    }

    LaunchedEffect(Unit) {
        launch {
            viewModel.screenEvent.collect { screenEvent ->
                when (screenEvent) {
                    is TranslationScreenEvent.CopyEvent -> {
                        clipboardManager.setText(AnnotatedString(screenEvent.text))
                        viewModel.setQuery("")
                    }

                    TranslationScreenEvent.ShowPreferences -> {
                        onShowPreferences()
                        viewModel.setQuery("")
                    }
                }
            }
        }

        launch {
            viewModel.snackbarEvent.collect {
                eventSnackbarState.showSnackbar(it)
            }
        }
    }
}