package dot.adun.vault.ui.components.core

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AnimatedIconContent(
    state: Boolean,
    icon1: DrawableRes,
    icon2: DrawableRes,
    modifier: Modifier = Modifier,
    content: @Composable (DrawableRes) -> Unit
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
            content(icon1)
        } else {
            content(icon2)
        }
    }
}

@Composable
fun AnimatedIconContent(
    state: Boolean,
    icon1: ImageVector,
    icon2: ImageVector,
    modifier: Modifier = Modifier,
    content: @Composable (ImageVector) -> Unit
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
            content(icon1)
        } else {
            content(icon2)
        }
    }
}