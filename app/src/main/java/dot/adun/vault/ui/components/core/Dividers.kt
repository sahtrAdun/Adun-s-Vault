package dot.adun.vault.ui.components.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VDivider(
    width: Dp = 2.dp,
    colors: List<Color>? = null,
    modifier: Modifier = Modifier
) {
    val backgroundColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
    val dividerGradient = listOf(
        Color.Transparent,
        backgroundColor,
        backgroundColor,
        backgroundColor,
        Color.Transparent
    )
    Box(
        modifier = modifier
            .width(width)
            .background(brush = Brush.verticalGradient(colors = colors ?: dividerGradient))
    )
}

@Composable
fun HDivider(
    height: Dp = 2.dp,
    colors: List<Color>? = null,
    modifier: Modifier = Modifier
) {
    val backgroundColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
    val dividerGradient = listOf(
        Color.Transparent,
        backgroundColor,
        backgroundColor,
        backgroundColor,
        Color.Transparent
    )
    Box(
        modifier = modifier
            .height(height)
            .background(brush = Brush.verticalGradient(colors = colors ?: dividerGradient))
    )
}

@Composable
fun VDivider(
    width: Dp = 2.dp,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(width)
            .background(color = color)
    )
}