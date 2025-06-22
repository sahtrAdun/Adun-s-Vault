package dot.adun.vault.ui.components.core

import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import dot.adun.vault.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CopyRow(
    value: String,
    background: Color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    height: Dp = 36.dp,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val clipboardManager = LocalClipboardManager.current

    val mainShimmerColor = MaterialTheme.colorScheme.primary
    val shimmerTheme = defaultShimmerTheme.copy(shaderColors = listOf(
        mainShimmerColor,
        mainShimmerColor.copy(alpha = 0.25f),
        mainShimmerColor
    ))
    val dividerGradient = listOf(
        background,
        Color.Transparent,
        Color.Transparent,
        Color.Transparent,
        Color.Transparent,
        Color.Transparent,
        background
    )
    var valueCopied by remember { mutableStateOf(false) }

    if (valueCopied == true) {
        scope.launch {
            delay(750)
            valueCopied = false
        }
    }

    Row(modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(modifier = Modifier
            .height(height)
            .weight(1f)
            .then(if(valueCopied) Modifier.shimmer(rememberShimmer(
                shimmerBounds = ShimmerBounds.View,
                theme = shimmerTheme
            )) else Modifier )
            .background(
                color = background,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                maxLines = 1,
                style = style,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    //.horizontalScroll(rememberScrollState())
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .basicMarquee(
                        iterations = Int.MAX_VALUE,
                        animationMode = MarqueeAnimationMode.Immediately,
                        repeatDelayMillis = 4000,
                        spacing = MarqueeSpacing(50.dp)
                    )
            )
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(brush = Brush.horizontalGradient(colors = dividerGradient))
            )
        }
        IconBox(
            size = height,
            background = background,
            onClick = {
                valueCopied = true
                clipboardManager.setText(AnnotatedString(value))
                onClick()
            }
        ) { iconModifier, color ->
            Icon(
                painter = painterResource(R.drawable.ic_copy),
                tint = color,
                contentDescription = "Copy icon",
                modifier = iconModifier
            )
        }
    }
}