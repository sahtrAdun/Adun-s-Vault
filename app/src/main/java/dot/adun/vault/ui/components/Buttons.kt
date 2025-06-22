package dot.adun.vault.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import dot.adun.vault.ui.components.core.ClickColorAnimation
import dot.adun.vault.ui.components.core.surface

@Composable
fun SimpleButton(
    text: String,
    containerColor: Color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
    contentColor: Color = MaterialTheme.colorScheme.outline,
    buttonShape: Shape = RoundedCornerShape(8.dp),
    enabled: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ClickColorAnimation(
        originalColor = contentColor,
        shape = buttonShape,
        onClick = { if (enabled) { onClick() } }
    ) { color, shape ->
        Box(modifier = modifier
            .surface(
                color = containerColor,
                shape = shape,
                innerPadding = PaddingValues(horizontal = 15.dp, vertical = 10.dp)
            )
        ) {
            Text(
                text = text,
                style = textStyle,
                color = color,
            )
        }
    }
}