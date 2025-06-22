package dot.adun.vault.ui.components.core

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@SuppressLint("UnnecessaryComposedModifier")
@Stable
fun Modifier.surface(
    color: Any,
    shape: Shape = RoundedCornerShape(0.dp),
    innerPadding: PaddingValues = PaddingValues(0.dp)
): Modifier = composed {
    val backgroundModifier = if (color is Color) {
        Modifier.background(color, shape = shape)
    } else if (color is Brush) {
        Modifier.background(brush = color, shape = shape)
    } else {
        Modifier
    }
    backgroundModifier.then(Modifier.padding(innerPadding))
}