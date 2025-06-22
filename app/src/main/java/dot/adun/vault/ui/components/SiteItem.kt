package dot.adun.vault.ui.components

import android.content.Intent
import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import dot.adun.vault.R
import dot.adun.vault.domain.entities.DecryptedSite
import dot.adun.vault.ui.components.core.CopyRow
import dot.adun.vault.ui.components.core.HDivider
import dot.adun.vault.ui.components.core.TextWithIcon

@Composable
fun SiteItem(
    site: DecryptedSite,
    last: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val safeUrl = site.url.sanitizeAndValidateUrl()
    val intent = remember(safeUrl) {
        safeUrl?.let { Intent(Intent.ACTION_VIEW, it.toUri()) }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = site.name,
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable(null, null) {
                    context.startActivity(intent)
                }
            )
            CopyRow(
                value = site.password,
                style = MaterialTheme.typography.bodySmall,
                height = 32.dp
            )
        }
        Text(
            text = site.id,
            maxLines = 1,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outlineVariant
        )
        LazyRow(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
        ) {
            item {
                TextWithIcon(
                    text = stringResource(R.string.b_delete),
                    icon = Icons.Filled.Delete
                ) { } // todo
            }
            item {
                TextWithIcon(
                    text = stringResource(R.string.b_history),
                    icon = Icons.Filled.Info
                ) { } // todo
            }
            item {
                TextWithIcon(
                    text = stringResource(R.string.b_edit),
                    icon = Icons.Filled.Edit
                ) { } // todo
            }
        }
        if (!last) {
            HDivider(modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(top = 10.dp)
                .align(Alignment.CenterHorizontally)
            )
        }
    }
}

private fun String.withHttpsPrefix(): String {
    return if (this.isEmpty()) {
        this
    } else if (!this.startsWith("http://", true) && !this.startsWith("https://",  true)) {
        "https://$this"
    } else {
        this
    }
}

private fun String.sanitizeAndValidateUrl(): String? {
    val urlWithPrefix = this.withHttpsPrefix()

    return if (Patterns.WEB_URL.matcher(urlWithPrefix).matches()) {
        urlWithPrefix
    } else {
        null
    }
}
