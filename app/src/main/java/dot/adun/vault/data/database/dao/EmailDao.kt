package dot.adun.vault.data.database.dao

import androidx.room.*
import dot.adun.vault.data.database.entities.EmailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmailDao {
    @Insert
    suspend fun insert(email: EmailEntity)

    @Update
    suspend fun update(email: EmailEntity)

    @Delete
    suspend fun delete(email: EmailEntity)

    @Query("SELECT * FROM emails")
    fun getAll(): Flow<List<EmailEntity>>
}