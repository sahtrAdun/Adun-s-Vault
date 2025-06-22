package dot.adun.vault.domain.entities

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
data class Site(
    val id: String,
    val name: String,
    val url: String,
    val encryptedPassword: EncryptedString
)

@Immutable
data class SiteInput(
    val name: String,
    val url: String,
    val password: String
)

@Immutable
@Serializable
data class DecryptedSite(
    val id: String,
    val name: String,
    val url: String,
    val password: String
)

