package dot.adun.vault.data.database.dao

import androidx.room.*
import dot.adun.vault.data.database.entities.SiteEntity

@Dao
interface SiteDao {
    @Insert
    suspend fun insert(site: SiteEntity)

    @Update
    suspend fun update(site: SiteEntity)

    @Delete
    suspend fun delete(site: SiteEntity)

    @Query("SELECT * FROM sites WHERE emailId = :emailId")
    suspend fun getByEmailId(emailId: String): List<SiteEntity>
}