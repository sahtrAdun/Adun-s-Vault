package dot.adun.vault.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import dot.adun.vault.R
import dot.adun.vault.ui.components.core.AnimatedIconContent
import dot.adun.vault.ui.components.core.IconBox
import dot.adun.vault.ui.components.core.VDivider
import dot.adun.vault.ui.components.core.VaultInputField
import dot.adun.vault.ui.components.core.onClick

@Composable
fun SearchAppbar(
    modifier: Modifier = Modifier,
    value: String,
    searchMode: Boolean = false,
    onSearchModeChange: () -> Unit,
    validateKeyAndDecryptEmails: () -> Unit,
    error: String? = null,
    onValueChange: (String) -> Unit
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val backgroundColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
    val tint = MaterialTheme.colorScheme.outline

    var rotation by remember { mutableFloatStateOf(0f) }
    val reloadAnimation by animateFloatAsState(
        targetValue = rotation,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )

    Column(modifier = modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
            .padding(horizontal = 15.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconBox(
                size = 36.dp,
                tint = tint,
                background = backgroundColor,
                onClick = {
                    rotation -= 360f
                    onSearchModeChange()
                }
            ) { iconModifier, color->
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_reload),
                    tint = color,
                    contentDescription = "Mode changing icon",
                    modifier = iconModifier.rotate(reloadAnimation)
                )
            }
            VDivider(modifier = Modifier.height(36.dp))
            VaultInputField(
                value = value,
                onValueChange = onValueChange,
                placeholder = if (searchMode) stringResource(R.string.main_search_placeholder) else stringResource(R.string.main_search_placeholder_key),
                height = 36.dp,
                error = (!error.isNullOrEmpty() && error == value),
                modifier = Modifier.weight(1f)
            )
            VDivider(modifier = Modifier.height(36.dp))
            IconBox(
                size = 36.dp,
                tint = tint,
                background = backgroundColor,
                onClick = {
                    if (value.isNotEmpty()) {
                        focusManager.clearFocus()
                        keyboard?.hide()
                        if (searchMode) { TODO() } else {
                            validateKeyAndDecryptEmails()
                        }
                    }
                }
            ) { iconModifier, color ->
                AnimatedIconContent(
                    state = searchMode,
                    icon1 = Icons.Filled.Search,
                    icon2 = Icons.Filled.Check
                ) { iconRes ->
                    Icon(
                        imageVector = iconRes,
                        tint = color,
                        contentDescription = "Search/apply key icon",
                        modifier = iconModifier
                    )
                }
            }
        }
    }
}