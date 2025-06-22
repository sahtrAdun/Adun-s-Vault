package dot.adun.vault.ui.components.core

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import kotlinx.coroutines.withContext

@Composable
fun IconBox(
    size: Dp = 32.dp,
    tint: Color = MaterialTheme.colorScheme.outline,
    background: Color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
    animationColor: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable (Modifier, Color) -> Unit
) {
    val scope = rememberCoroutineScope()

    var clicked by remember { mutableStateOf(false) }
    val animation by animateColorAsState(
        targetValue = if (clicked) animationColor else tint,
        animationSpec = tween(200)
    )

    Box(modifier = modifier
        .size(size)
        .background(
            color = background,
            shape = RoundedCornerShape(8.dp)
        )
        .clickable() {
            scope.launch {
                clicked = true
                kotlinx.coroutines.delay(200)
                clicked = false
            }
            onClick()
        },
        contentAlignment = Alignment.Center
    ) {
        content(Modifier.size(size * 0.6f), animation)
    }
}
