package dot.adun.vault.domain.encryption

import android.util.Base64
import android.util.Log
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

class CipherWrapper(private val encryptionKey: String) {

    companion object {
        private const val KEY_ALGORITHM = "AES"
        private const val CIPHER_TRANSFORMATION = "AES/GCM/NoPadding"
        private const val GCM_TAG_LENGTH = 128 // in bits
        private const val IV_LENGTH_BYTES = 12 // in bytes
    }

    fun encrypt(plainText: String): String {
        val cipher = Cipher.getInstance(CIPHER_TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getKey())
        val iv = cipher.iv
        val cipherText = cipher.doFinal(plainText.toByteArray())
        val combined = ByteArray(iv.size + cipherText.size).apply {
            System.arraycopy(iv, 0, this, 0, iv.size)
            System.arraycopy(cipherText, 0, this, iv.size, cipherText.size)
        }
        return Base64.encodeToString(combined, Base64.DEFAULT)
    }

    fun decrypt(cipherText: String): String? {
        try {
            val decoded = Base64.decode(cipherText, Base64.DEFAULT)
            val cipher = Cipher.getInstance(CIPHER_TRANSFORMATION)
            val iv = ByteArray(IV_LENGTH_BYTES).apply { System.arraycopy(decoded, 0, this, 0, size) }
            val cipherTextBytes = ByteArray(decoded.size - IV_LENGTH_BYTES)
                .apply { System.arraycopy(decoded, IV_LENGTH_BYTES, this, 0, size) }

            cipher.init(Cipher.DECRYPT_MODE, getKey(), GCMParameterSpec(GCM_TAG_LENGTH, iv))
            val decrypted = cipher.doFinal(cipherTextBytes)
            return String(decrypted)
        } catch (e: Exception) {
            Log.e("KEY-DECRYPTION", e.message.toString())
            return null // ошибка дешифровки (неверный ключ)
        }
    }

    private fun getKey(): SecretKeySpec {
        val keyBytes = Base64.decode(encryptionKey, Base64.DEFAULT)
        return SecretKeySpec(keyBytes, KEY_ALGORITHM)
    }
}