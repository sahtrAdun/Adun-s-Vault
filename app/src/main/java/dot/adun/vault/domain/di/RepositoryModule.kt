package dot.adun.vault.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dot.adun.vault.data.LocalDataSource
import dot.adun.vault.data.VaultDataRepository
import dot.adun.vault.data.database.dao.EmailDao
import dot.adun.vault.data.database.dao.SiteDao
import dot.adun.vault.data.mappers.EmailMapper
import dot.adun.vault.data.mappers.SiteMapper
import dot.adun.vault.domain.VaultRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideEmailMapper() = EmailMapper()

    @Provides
    @Singleton
    fun provideSiteMapper() = SiteMapper()

    @Provides
    @Singleton
    fun provideLocalDataSource(
        emailDao: EmailDao,
        siteDao: SiteDao,
        emailMapper: EmailMapper,
        siteMapper: SiteMapper
    ): LocalDataSource {
        return LocalDataSource(emailDao, siteDao, emailMapper, siteMapper)
    }

    @Provides
    @Singleton
    fun provideVaultRepository(repo: VaultDataRepository): VaultRepository = repo
}