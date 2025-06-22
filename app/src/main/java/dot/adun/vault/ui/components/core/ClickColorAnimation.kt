package dot.adun.vault.ui.components.core

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ClickColorAnimation(
    originalColor: Color = MaterialTheme.colorScheme.outline,
    animationColor: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = RoundedCornerShape(8.dp),
    onClick: () -> Unit,
    content: @Composable (Color, Shape) -> Unit
) {
    val scope = rememberCoroutineScope()
    var clicked by remember { mutableStateOf(false) }
    val animation by animateColorAsState(
        targetValue = if (clicked) animationColor else originalColor,
        animationSpec = tween(250)
    )
    Box(Modifier.clip(shape).clickable {
        scope.launch { clicked = true; delay(300); clicked = false }
        onClick()
    }
    ) {
        content(animation, shape)
    }

}