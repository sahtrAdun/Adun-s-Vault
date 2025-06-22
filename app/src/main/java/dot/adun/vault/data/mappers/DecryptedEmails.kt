package dot.adun.vault.data.mappers

import android.util.Log
import dot.adun.vault.domain.entities.DecryptedEmail
import dot.adun.vault.domain.entities.DecryptedSite
import dot.adun.vault.domain.entities.Email
import dot.adun.vault.domain.entities.EncryptedString
import dot.adun.vault.domain.entities.Site

fun Email.toDecrypted(): DecryptedEmail {
    return DecryptedEmail(
        id = this.id,
        email = this.encryptedEmail.value,
        sites = this.sites.map { it.toDecrypted() }
    )
}

fun Site.toDecrypted(): DecryptedSite {
    return DecryptedSite(
        id = this.id,
        name = this.name,
        url = this.url,
        password = this.encryptedPassword.value
    )
}

/*fun DecryptedEmail.toEmail(encryptedEmail: String): Email {
    return Email(
        id = this.id,
        encryptedEmail = EncryptedString(encryptedEmail),
        sites = this.sites.map { it.toSite() }
    )
}

fun DecryptedSite.toSite(encryptedPassword: String): Site {
    return Site(
        id = this.id,
        name = this.name,
        url = this.url,
        encryptedPassword = EncryptedString(encryptedPassword)
    )
}*/

fun mapAll(
    emails: List<Email>,
    decryptedEmails: Map<String, String>,
    decryptedPasswords: Map<String, String>
): List<DecryptedEmail> {
    Log.d("EMAIL-input", "$emails")
    Log.d("EMAIL-decrypted", "$decryptedEmails")
    return emails.map { email ->
        val decryptedEmail = decryptedEmails[email.id] ?: return emptyList()
        val decryptedSites = email.sites.map { site ->
            val password = decryptedPasswords[site.id] ?: return emptyList()
            DecryptedSite(
                id = site.id,
                name = site.name,
                url = site.url,
                password = password
            )
        }
        Log.d("EMAIL-decryptedEmail", "$decryptedEmail")
        Log.d("EMAIL-decryptedSites", "$decryptedSites")
        val kal = DecryptedEmail(id = email.id, email = decryptedEmail, sites = decryptedSites)
        Log.d("EMAIL-after-decryption", "$kal")
        kal
    }
}