package dot.adun.vault.data.mappers

import dot.adun.vault.data.database.entities.SiteEntity
import dot.adun.vault.domain.entities.EncryptedString
import dot.adun.vault.domain.entities.Site
import jakarta.inject.Inject

class SiteMapper @Inject constructor() {
    fun toDomain(entity: SiteEntity): Site {
        return Site(
            id = entity.id,
            name = entity.name,
            url = entity.url,
            encryptedPassword = EncryptedString(entity.encryptedPassword)
        )
    }

    fun toEntity(domain: Site, emailId: String): SiteEntity {
        return SiteEntity(
            emailId = emailId,
            name = domain.name,
            url = domain.url,
            encryptedPassword = domain.encryptedPassword.value
        )
    }
}