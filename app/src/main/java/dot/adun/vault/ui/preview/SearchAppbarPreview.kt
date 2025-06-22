package dot.adun.vault.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dot.adun.vault.ui.components.SearchAppbar

@Preview
@Composable
private fun SearchAppbarPreview() {
    BasePreview {
        SearchAppbar(
            value = "",
            onValueChange = {},
            searchMode = false,
            onSearchModeChange = {},
            validateKeyAndDecryptEmails = {}
        )
    }
}