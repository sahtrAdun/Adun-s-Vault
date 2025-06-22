package dot.adun.vault.ui.components.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IconBox(
    size: Dp = 32.dp,
    tint: Color = MaterialTheme.colorScheme.outline,
    background: Color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable (Modifier, Color) -> Unit
) {
    ClickColorAnimation(
        originalColor = tint,
        onClick = onClick
    ) { animation, shape ->
        Box(modifier = modifier
            .size(size)
            .background(
                color = background,
                shape = shape
            ),
            contentAlignment = Alignment.Center
        ) {
            content(Modifier.size(size * 0.6f), animation)
        }
    }
}
