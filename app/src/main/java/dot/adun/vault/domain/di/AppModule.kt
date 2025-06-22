package dot.adun.vault.domain.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dot.adun.vault.data.database.AppDatabase
import dot.adun.vault.data.database.dao.EmailDao
import dot.adun.vault.data.database.dao.SiteDao
import dot.adun.vault.ui.components.onboarding.FirstLaunchDataStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "aduns_vault.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFirstLaunchDataStore(@ApplicationContext context: Context): FirstLaunchDataStore {
        return FirstLaunchDataStore(context)
    }

    @Provides
    @Singleton
    fun provideEmailDao(database: AppDatabase): EmailDao {
        return database.emailDao()
    }

    @Provides
    @Singleton
    fun provideSiteDao(database: AppDatabase): SiteDao {
        return database.siteDao()
    }
}