package dot.adun.vault.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dot.adun.vault.data.database.dao.EmailDao
import dot.adun.vault.data.database.dao.SiteDao
import dot.adun.vault.data.database.entities.EmailEntity
import dot.adun.vault.data.database.entities.SiteEntity

@Database(entities = [EmailEntity::class, SiteEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun emailDao(): EmailDao
    abstract fun siteDao(): SiteDao
}