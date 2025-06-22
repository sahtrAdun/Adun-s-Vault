package dot.adun.vault.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dot.adun.vault.R
import dot.adun.vault.domain.entities.DecryptedEmail
import dot.adun.vault.ui.components.core.CopyRow
import dot.adun.vault.ui.components.core.TextWithIcon
import dot.adun.vault.ui.components.core.VDivider
import dot.adun.vault.ui.components.core.surface
import dot.adun.vault.ui.util.looksLikeEncrypted

@Composable
fun EmailItem(
    email: DecryptedEmail,
    editEmail: (String) -> Unit,
    deleteEmail: (String) -> Unit,
    addNewSite: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val backgroundColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)

    val rotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )

    Column(modifier = modifier
        .fillMaxWidth()
        .animateContentSize()
        .surface(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(8.dp),
            innerPadding = PaddingValues(horizontal = 15.dp, vertical = 10.dp)
        )
    ) {
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    .rotate(rotation)
                    .clickable(null, null) { expanded = !expanded }
            )
            VDivider(modifier = Modifier.height(48.dp))
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                CopyRow(
                    value = if (email.email.looksLikeEncrypted()) "●●●●●●●●" else email.email,
                    background = backgroundColor
                )
                Text(
                    text = email.id,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }
        }
        AnimatedVisibility(expanded) {
            Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                LazyRow(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
                ) {
                    item {
                        TextWithIcon(
                            text = stringResource(R.string.b_delete),
                            icon = Icons.Filled.Delete
                        ) { deleteEmail(email.id) }
                    }
                    item {
                        TextWithIcon(
                            text = stringResource(R.string.b_edit),
                            icon = Icons.Filled.Edit
                        ) { editEmail(email.id) }
                    }
                }
                if (email.sites.isNotEmpty()) {
                    Text(
                        text = stringResource(R.string.main_sites_list) + ":",
                        color = MaterialTheme.colorScheme.outline,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                email.sites.forEachIndexed { index, site ->
                    SiteItem(
                        site = site,
                        last = index == email.sites.size - 1
                    )
                }
                AddNewSite { addNewSite() }
            }
        }
    }
}
