package dot.adun.vault.ui.components.core

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
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
            .height(36.dp)
            .weight(1f)
            .then(if(valueCopied) Modifier.shimmer(rememberShimmer(
                shimmerBounds = ShimmerBounds.View,
                theme = shimmerTheme
            )) else Modifier )
            .surface(
                color = background,
                shape = RoundedCornerShape(8.dp),
                innerPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
            ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.horizontalScroll(rememberScrollState())
            )
        }
        IconBox(
            size = 36.dp,
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