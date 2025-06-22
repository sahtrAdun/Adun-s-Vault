package dot.adun.vault.domain.encryption

import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import android.util.Base64

object KeyGenerator {
    fun generateEncryptionKey(): String {
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(256) // AES-256
        val secretKey: SecretKey = keyGen.generateKey()
        return Base64.encodeToString(secretKey.encoded, Base64.DEFAULT)
    }
}