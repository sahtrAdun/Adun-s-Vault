package dot.adun.vault.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dot.adun.vault.R
import dot.adun.vault.domain.entities.DecryptedSite
import dot.adun.vault.ui.components.core.ClickColorAnimation
import dot.adun.vault.ui.components.core.HDivider
import dot.adun.vault.ui.components.core.VaultInputField
import dot.adun.vault.ui.components.core.surface

@Composable
fun AddNewSite(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val tint = MaterialTheme.colorScheme.outlineVariant
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        HDivider(modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = 10.dp)
            .align(Alignment.CenterHorizontally)
        )
        ClickColorAnimation(
            originalColor = tint,
            onClick = { onClick() }
        ) { color, shape ->
            Row(modifier = modifier
                .fillMaxWidth()
                .height(36.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier
                    .surface(
                        color = Color.Transparent,
                        shape = shape,
                        innerPadding = PaddingValues(10.dp)
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = null,
                            tint = color,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = stringResource(R.string.item_new_site),
                            color = color,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddNewSiteDialog(
    addSite: (DecryptedSite) -> Unit,
    onClose: () -> Unit
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    var name by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Dialog(onDismissRequest = {}) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceContainer,
            modifier = Modifier.clickable(null, null) {
                focusManager.clearFocus()
                keyboard?.hide()
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Text(
                    text = stringResource(R.string.dialog_new_site_header),
                    style = MaterialTheme.typography.bodyMedium
                )
                InputRow(stringResource(R.string.new_site_name)) {
                    VaultInputField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = "GitHub",
                        height = 36.dp,
                        type = KeyboardType.Text,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                InputRow(stringResource(R.string.new_site_address)) {
                    VaultInputField(
                        value = url,
                        onValueChange = { url = it },
                        placeholder = "gitnub.com",
                        height = 36.dp,
                        type = KeyboardType.Text,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                InputRow(stringResource(R.string.new_site_password)) {
                    VaultInputField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = "********",
                        height = 36.dp,
                        type = KeyboardType.Password,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End)
                ) {
                    val allow = url.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty()
                    SimpleButton(stringResource(R.string.dialog_button_cancel)) {
                        keyboard?.hide()
                        focusManager.clearFocus()
                        onClose()
                    }
                    SimpleButton(stringResource(R.string.dialog_button_confirm)) {
                        if (allow) {
                            keyboard?.hide()
                            focusManager.clearFocus()
                            addSite(DecryptedSite(
                                id = "",
                                name = name,
                                url = url,
                                password = password
                            ))
                            onClose()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InputRow(
    label: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
        content()
    }
}
