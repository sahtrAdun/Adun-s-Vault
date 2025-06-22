package dot.adun.vault.ui.components.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import dot.adun.vault.ui.SnackMessage
import dot.adun.vault.ui.SnackMessageType

@Composable
fun VaultSnackbar(
    snack: SnackMessage,
    isRtl: Boolean = true,
    modifier: Modifier = Modifier
) {
    var contentColor: Color = MaterialTheme.colorScheme.outline
    var containerColor: Color = MaterialTheme.colorScheme.surface

    var closed by remember { mutableStateOf(false) }

    when(snack.type) {
        SnackMessageType.Error -> {
            contentColor = MaterialTheme.colorScheme.error
            containerColor = contentColor.copy(alpha = 0.15f)
        }
        SnackMessageType.Success -> {
            contentColor = MaterialTheme.colorScheme.primary
            containerColor = contentColor.copy(alpha = 0.15f)
        }
        else -> Unit
    }

    Snackbar(
        containerColor = containerColor,
        modifier = modifier
            .padding(horizontal = 15.dp, vertical = 8.dp),
    ) {
        CompositionLocalProvider(
            LocalLayoutDirection provides
                if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
        ) {
            Text(
                snack.message,
                style = MaterialTheme.typography.bodyMedium,
                color = contentColor
            )
        }
    }
}