package dot.adun.vault.ui.components.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

@Composable
fun TextWithIcon(
    text: String,
    icon: Int,
    tint: Color = MaterialTheme.colorScheme.outline,
    background: Color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.25f),
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    ClickColorAnimation(
        originalColor = tint,
        onClick = { onClick() }
    ) { animation, shape ->
        Box(modifier = modifier
            .background(
                color = background,
                shape = shape
            ),
            contentAlignment = Alignment.Center
        ) {
            Row(modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall,
                    color = animation
                )
                Icon(
                    imageVector = ImageVector.vectorResource(icon),
                    contentDescription = null,
                    tint = animation,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun TextWithIcon(
    text: String,
    icon: ImageVector,
    tint: Color = MaterialTheme.colorScheme.outline,
    background: Color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.25f),
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    ClickColorAnimation(
        originalColor = tint,
        onClick = { onClick() }
    ) { animation, shape ->
        Box(modifier = modifier
            .background(
                color = background,
                shape = shape
            ),
            contentAlignment = Alignment.Center
        ) {
            Row(modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall,
                    color = animation
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = animation,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
