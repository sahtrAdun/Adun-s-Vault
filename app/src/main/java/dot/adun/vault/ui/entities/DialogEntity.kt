package dot.adun.vault.ui.entities

import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import dot.adun.vault.domain.entities.DecryptedEmail

@Immutable
sealed class DialogConfig {
    data class NewEmail(
        val modifier: Modifier = Modifier,
        val addEmail: (String) -> Unit,
        val onClose: () -> Unit
    ) : DialogConfig()
    data class DeleteEmail(
        val onApprove: () -> Unit,
        val onCancel: () -> Unit
    ) : DialogConfig()
    data class EditEmail(val email: DecryptedEmail, val onUpdate: (DecryptedEmail) -> Unit) : DialogConfig()
    data class NewSite(val email: DecryptedEmail, val onAdd: (String) -> Unit) : DialogConfig()
}