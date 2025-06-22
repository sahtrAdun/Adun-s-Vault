package dot.adun.vault.domain

import dot.adun.vault.domain.entities.Email
import dot.adun.vault.domain.entities.Site
import kotlinx.coroutines.flow.Flow

interface VaultRepository {
    fun getAllEmails(): Flow<List<Email>>
    suspend fun getEmails(): List<Email>
    suspend fun addEmail(email: Email)
    suspend fun updateEmail(email: Email)
    suspend fun deleteEmail(email: Email)
    suspend fun addSiteTo(emailId: String, site: Site)
}