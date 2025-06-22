package dot.adun.vault.ui

import android.annotation.SuppressLint
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dot.adun.vault.ui.components.MainLayout
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val intents = rememberMainIntents(viewModel)
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.snackMessage) {
        if (state.snackMessage != null) {
            snackbarHostState.showSnackbar(message = Json.encodeToString<SnackMessage>(state.snackMessage!!))
        }
    }

    MainLayout(
        intents = intents,
        init = state.init,
        searchMode = state.searchMode,
        searchText = state.searchText,
        keyText = state.keyText.orEmpty(),
        emails = state.actualEmails,
        error = state.error,
        snackbarHostState = snackbarHostState,
        isKeyValid = state.isKeyValid,
        modifier = modifier
    )
}

@Composable
private fun rememberMainIntents(viewModel: MainViewModel): MainIntentsWrapper {
    val scope = rememberCoroutineScope()
    return remember {
        MainIntentsWrapper { intent ->
            scope.launch {
                viewModel.onIntent(intent)
            }
        }
    }
}