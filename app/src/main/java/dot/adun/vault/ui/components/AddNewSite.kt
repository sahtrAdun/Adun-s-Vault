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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dot.adun.vault.R
import dot.adun.vault.ui.components.core.HDivider
import dot.adun.vault.ui.components.core.surface

@Composable
fun AddNewSite(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        HDivider(modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = 10.dp)
            .align(Alignment.CenterHorizontally)
        )
        Row(modifier = modifier
            .fillMaxWidth()
            .height(36.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier
                .surface(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(8.dp),
                    innerPadding = PaddingValues(10.dp)
                )
                .clickable { onClick() }
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outlineVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = stringResource(R.string.item_new_site),
                        color = MaterialTheme.colorScheme.outlineVariant,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun AddNewSiteDialog(

) {

}
