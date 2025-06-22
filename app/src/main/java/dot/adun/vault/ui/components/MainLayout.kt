package dot.adun.vault.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import dot.adun.vault.domain.entities.DecryptedEmail
import dot.adun.vault.ui.MainIntentsWrapper
import dot.adun.vault.ui.SnackMessage
import dot.adun.vault.ui.components.core.LceContent
import dot.adun.vault.ui.components.core.VSpacer
import dot.adun.vault.ui.components.core.VaultDialogManager
import dot.adun.vault.ui.components.core.VaultSnackbar
import dot.adun.vault.ui.entities.DialogConfig
import dot.adun.vault.ui.util.checkKey
import kotlinx.serialization.json.Json

@Composable
fun MainLayout(
    modifier: Modifier = Modifier,
    intents: MainIntentsWrapper,
    snackbarHostState: SnackbarHostState,
    init: Boolean,
    searchMode: Boolean,
    isKeyValid: Boolean,
    searchText: String,
    keyText: String,
    error: String?,
    emails: List<DecryptedEmail>,
) {
    val context = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    var currentDialog by remember { mutableStateOf<DialogConfig?>(null) }

    LceContent(
        loadingCondition = init,
        errorCondition = false,
        modifier = modifier
    ) { innerModifier ->
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = Color.Transparent,
            topBar = { SearchAppbar(
                value = if (searchMode) searchText else keyText,
                onValueChange = if (searchMode) intents::updateSearchText else intents::updateKeyText,
                searchMode = searchMode,
                onSearchModeChange = intents::searchModeChange,
                validateKeyAndDecryptEmails = intents::validateKey,
                error = error
            ) },
            floatingActionButton = {
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.surface,
                    shape = CircleShape,
                    modifier = Modifier.size(48.dp),
                    onClick = {
                        context.checkKey(isKeyValid, intents) {
                            currentDialog = DialogConfig.NewEmail(
                                modifier = Modifier,
                                addEmail = intents::addNewEmail,
                                onClose = { currentDialog = null }
                            )
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        tint = MaterialTheme.colorScheme.outline,
                        contentDescription = "New email icon",
                        modifier = Modifier.size(32.dp)
                    )
                }
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) { data ->
                val snack = Json.decodeFromString<SnackMessage>(data.visuals.message)
                VaultSnackbar(snack)
            } },
            modifier = innerModifier
                .fillMaxSize()
                .clickable(null, null) {
                    focusManager.clearFocus()
                    keyboard?.hide()
                },
        ) { pd ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(pd)
                    .padding(horizontal = 15.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                item { VSpacer(10.dp) }
                items(emails) { email ->
                    EmailItem(
                        email = email,
                        editEmail = { id ->
                            context.checkKey(isKeyValid, intents) {
                                currentDialog = DialogConfig.EditEmail(
                                    email = email.email,
                                    onCancel = { currentDialog = null },
                                    onApprove = {
                                        intents.editEmail(id)
                                        currentDialog = null
                                    }
                                )
                            }
                        },
                        deleteEmail = { id ->
                            context.checkKey(isKeyValid, intents) {
                                currentDialog = DialogConfig.DeleteEmail(
                                    onCancel = { currentDialog = null },
                                    onApprove = {
                                        intents.deleteEmail(id)
                                        currentDialog = null
                                    }
                                )
                            }
                        },
                        addNewSite = {
                            context.checkKey(isKeyValid, intents) {
                                currentDialog = DialogConfig.NewSite(
                                    onClose = { currentDialog = null },
                                    addSite = { site ->
                                        intents.addNewSiteTo(email.id, site)
                                        currentDialog = null
                                    }
                                )
                            }
                        }
                    )
                }
                item { VSpacer(50.dp) }
            }
        }
    }

    currentDialog?.let { VaultDialogManager(currentDialog) }
}