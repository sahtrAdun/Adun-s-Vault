package dot.adun.vault.data

import dot.adun.vault.data.database.dao.EmailDao
import dot.adun.vault.data.database.dao.SiteDao
import dot.adun.vault.data.mappers.EmailMapper
import dot.adun.vault.data.mappers.SiteMapper
import dot.adun.vault.domain.entities.Email
import dot.adun.vault.domain.entities.Site
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val emailDao: EmailDao,
    private val siteDao: SiteDao,
    private val emailMapper: EmailMapper,
    private val siteMapper: SiteMapper
) {
    suspend fun getEmails(): List<Email> {
        val emails = emailDao.getAll().map { emailEntities ->
            emailEntities.map { emailEntity ->
                val siteEntities = siteDao.getByEmailId(emailEntity.id)
                val sites = siteEntities.map(siteMapper::toDomain)
                emailMapper.toDomain(emailEntity, sites)
            }
        }
        return emails.first()
    }

    fun getEmailsFlow(): Flow<List<Email>> = flow {
        emailDao.getAll().map { list ->
            list.map { emailEntity ->
                val siteEntities = siteDao.getByEmailId(emailEntity.id)
                val sites = siteEntities.map(siteMapper::toDomain)
                emailMapper.toDomain(emailEntity, sites)
            }
        }.collect {
            emit(it)
        }
    }

    suspend fun addEmail(email: Email) {
        val emailEntity = emailMapper.toEntity(email)
        emailDao.insert(emailEntity)

        email.sites.forEach { site ->
            val siteEntity = siteMapper.toEntity(site, emailEntity.id)
            siteDao.insert(siteEntity)
        }
    }

    suspend fun updateEmail(email: Email) {
        val emailEntity = emailMapper.toEntity(email)
        emailDao.update(emailEntity)
    }

    suspend fun deleteEmail(email: Email) {
        val emailEntity = emailMapper.toEntity(email)
        emailDao.delete(emailEntity)
    }

    suspend fun addSiteTo(
        emailId: String,
        site: Site
    ) {
        val siteEntity = siteMapper.toEntity(site, emailId)
        siteDao.insert(siteEntity)
    }
}
