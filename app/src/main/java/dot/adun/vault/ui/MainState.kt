package dot.adun.vault.ui

import androidx.compose.runtime.Immutable
import dot.adun.vault.domain.entities.DecryptedEmail
import dot.adun.vault.domain.entities.Email
import kotlinx.serialization.Serializable

@Immutable
data class MainState(
    val init: Boolean = true,
    val isLoading: Boolean = false,
    val searchMode: Boolean = false,
    val emails: List<Email> = emptyList(),
    val decryptedEmails: Map<String, String> = emptyMap(), // ID -> Email
    val decryptedPasswords: Map<String, String> = emptyMap(), // SiteID -> Password
    val actualEmails: List<DecryptedEmail> = emptyList(),
    val searchText: String = "",
    val keyText: String? = null,
    val isKeyValid: Boolean = false,
    val error: String? = null,
    val snackMessage: SnackMessage? = null
)

@Immutable
@Serializable
data class SnackMessage(
    val message: String,
    val type: SnackMessageType = SnackMessageType.Notification
)

@Serializable
enum class SnackMessageType(val text: String) {
    Error("Error"), Success("Success"), Notification("Notification")
}