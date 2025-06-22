package dot.adun.vault.data.database.entities

import androidx.room.*
import java.util.UUID

@Entity(
    tableName = "sites",
    foreignKeys = [
        ForeignKey(
            entity = EmailEntity::class,
            parentColumns = ["id"],
            childColumns = ["emailId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("emailId")]
)
data class SiteEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val emailId: String,
    val name: String,
    val url: String,
    val encryptedPassword: String
)