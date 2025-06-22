package dot.adun.vault.data.mappers

import dot.adun.vault.data.database.entities.EmailEntity
import dot.adun.vault.domain.entities.Email
import dot.adun.vault.domain.entities.EncryptedString
import dot.adun.vault.domain.entities.Site
import javax.inject.Inject

class EmailMapper @Inject constructor() {
    fun toDomain(entity: EmailEntity, sites: List<Site>): Email {
        return Email(
            id = entity.id,
            encryptedEmail = EncryptedString(entity.encryptedEmail),
            sites = sites
        )
    }

    fun toEntity(domain: Email): EmailEntity {
        return EmailEntity(
            encryptedEmail = domain.encryptedEmail.value
        )
    }
}