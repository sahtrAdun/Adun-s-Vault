package dot.adun.vault.ui.components.core

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AnimatedTextContent(
    state: Boolean,
    text1: String,
    text2: String,
    modifier: Modifier = Modifier,
    content: @Composable (String) -> Unit
) {
    AnimatedContent(
        targetState = state,
        modifier = modifier,
        transitionSpec = {
            (slideInHorizontally(
                animationSpec = spring(stiffness = Spring.StiffnessLow),
                initialOffsetX = { -it }
            ) + fadeIn()).togetherWith(
                slideOutHorizontally(
                    animationSpec = spring(stiffness = Spring.StiffnessLow),
                    targetOffsetX = { it }
                ) + fadeOut())
        },
        label = "Animated Icon Switch"
    ) { targetState ->
        if (targetState) {
            content(text1)
        } else {
            content(text2)
        }
    }
}