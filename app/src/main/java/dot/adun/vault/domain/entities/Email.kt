package dot.adun.vault.domain.entities

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
data class Email(
    val id: String,
    val encryptedEmail: EncryptedString,
    val sites: List<Site>
)
@Immutable
@Serializable
data class DecryptedEmail(
    val id: String,
    val email: String,
    val sites: List<DecryptedSite>
)
