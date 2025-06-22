package dot.adun.vault.ui.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dot.adun.vault.ui.theme.AdunsVaultTheme

@Composable
fun BasePreview(
    content: @Composable () -> Unit
) {
    AdunsVaultTheme {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            content()
        }
    }
}