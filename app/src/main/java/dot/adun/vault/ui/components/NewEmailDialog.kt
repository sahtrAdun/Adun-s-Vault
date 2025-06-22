package dot.adun.vault.ui.components

import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dot.adun.vault.R
import dot.adun.vault.ui.components.core.VaultInputField

@Composable
fun NewEmailDialog(
    modifier: Modifier = Modifier,
    addEmail: (String) -> Unit,
    onClose: () -> Unit
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    var value by remember { mutableStateOf("") }

    Dialog(onDismissRequest = {}) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceContainer,
            modifier = modifier.clickable(null, null) {
                focusManager.clearFocus()
                keyboard?.hide()
            }
        ) {
            Column(modifier = Modifier
                .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Text(
                    text = stringResource(R.string.dialog_new_email_header),
                    style = MaterialTheme.typography.bodyMedium
                )
                VaultInputField(
                    value = value,
                    onValueChange = { value = it },
                    placeholder = "example@mail.com",
                    height = 36.dp,
                    type = KeyboardType.Email,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End)
                ) {
                    val allow = value.isNotEmpty() && validateEmail(value)
                    SimpleButton(stringResource(R.string.dialog_button_cancel)) {
                        keyboard?.hide()
                        focusManager.clearFocus()
                        onClose()
                    }
                    SimpleButton(stringResource(R.string.dialog_button_ok)) {
                        if (allow) {
                            keyboard?.hide()
                            focusManager.clearFocus()
                            addEmail(value)
                            onClose()
                        }
                    }
                }
            }
        }
    }
}

private fun validateEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()