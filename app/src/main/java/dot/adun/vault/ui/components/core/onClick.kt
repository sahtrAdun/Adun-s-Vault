package dot.adun.vault.ui.components.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color

fun Modifier.onClick(
    color: Color = Color.Green.copy(alpha = 0.25f),
    onClick: () -> Unit
) = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = ripple(color = color),
        onClick = onClick
    )
}
