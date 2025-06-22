package dot.adun.vault.data.database.entities

import androidx.room.*
import java.util.UUID

@Entity(tableName = "emails")
data class EmailEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val encryptedEmail: String
)

