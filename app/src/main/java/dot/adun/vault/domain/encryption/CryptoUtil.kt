package dot.adun.vault.domain.encryption

object CryptoUtil {
    const val VALIDATION_CIPHER = "VALIDATION_CIPHER"

    fun validateKey(key: String, storedCipher: String): Boolean {
        if (storedCipher.isEmpty()) return false
        val cipher = CipherWrapper(key.trim())
        val decrypted = cipher.decrypt(storedCipher)
        return decrypted == VALIDATION_CIPHER
    }

    fun generateValidationCipher(key: String): String {
        val cipher = CipherWrapper(key)
        return cipher.encrypt(VALIDATION_CIPHER)
    }
}
