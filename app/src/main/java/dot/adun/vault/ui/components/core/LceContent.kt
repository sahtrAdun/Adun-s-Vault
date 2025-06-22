package dot.adun.vault.ui.components.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LceContent(
    modifier: Modifier = Modifier,
    loadingCondition: Boolean,
    errorCondition: Boolean = false,
    loadingContent: @Composable (Modifier) -> Unit = { Loading(it) },
    errorContent: @Composable (Modifier) -> Unit = {  },
    content: @Composable (Modifier) -> Unit
) {
    if (loadingCondition) {
        println("LceContent -- Load")
        loadingContent(modifier)
    } else if (errorCondition) {
        println("LceContent -- Error")
        errorContent(modifier)
    } else {
        println("LceContent -- Ready")
        content(modifier)
    }
}