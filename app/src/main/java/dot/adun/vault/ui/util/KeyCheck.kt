package dot.adun.vault.ui.util

import android.content.Context
import dot.adun.vault.R
import dot.adun.vault.ui.MainIntentsWrapper
import dot.adun.vault.ui.SnackMessageType

fun Context.checkKey(
    condition: Boolean,
    intents: MainIntentsWrapper,
    onAllow: () -> Unit
) {
    if (condition) {
        onAllow()
    } else {
        intents.emitSnackMessage(
            message = getString(R.string.error_key),
            type = SnackMessageType.Error
        )
    }
}