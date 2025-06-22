package dot.adun.vault.data

import dot.adun.vault.domain.VaultRepository
import dot.adun.vault.domain.entities.Email
import dot.adun.vault.domain.entities.Site
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class VaultDataRepository @Inject constructor(
    private val localDataSource: LocalDataSource
) : VaultRepository {

    override fun getAllEmails(): Flow<List<Email>> {
        return localDataSource.getEmailsFlow()
    }

    override suspend fun getEmails(): List<Email> {
        return localDataSource.getEmails()
    }

    override suspend fun addEmail(email: Email) {
        localDataSource.addEmail(email)
    }

    override suspend fun updateEmail(email: Email) {
        localDataSource.updateEmail(email)
    }

    override suspend fun deleteEmail(email: Email) {
        localDataSource.deleteEmail(email)
    }

    override suspend fun addSiteTo(
        emailId: String,
        site: Site
    ) {
        localDataSource.addSiteTo(emailId, site)
    }
}
