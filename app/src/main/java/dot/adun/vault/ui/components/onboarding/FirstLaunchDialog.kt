package dot.adun.vault.ui.components.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dot.adun.vault.R
import dot.adun.vault.ui.components.core.CopyRow
import dot.adun.vault.ui.components.core.IconBox
import dot.adun.vault.ui.components.core.VDivider

@Composable
fun FirstLaunchDialog(
    onFirstLaunchComplete: (key: String) -> Unit
) {
    val viewModel: FirstLaunchViewModel = viewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Dialog(
        onDismissRequest = {}
    ) {
        val backgroundColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceContainer
        ) {
            Column(modifier = Modifier
                .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Text(
                    text = stringResource(R.string.first_launch_dialog_header) + ":",
                    style = MaterialTheme.typography.bodyMedium
                )
                CopyRow(value = state.encryptionKey?: "Generating") {
                    viewModel.onIntent(FirstLaunchIntent.CopyKeyToClipboard)
                }
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = backgroundColor,
                            contentColor = if (state.isKeyCopied) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.outlineVariant
                        ),
                        shape = RoundedCornerShape(8.dp),
                        onClick = {
                            if (state.isKeyCopied) {
                                viewModel.onIntent(FirstLaunchIntent.Confirm)
                                state.encryptionKey?.let { onFirstLaunchComplete(it) }
                            }
                        },
                        enabled = state.isKeyCopied
                    ) {
                        Text(text = "Ok")
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onIntent(FirstLaunchIntent.GenerateKey)
    }
}