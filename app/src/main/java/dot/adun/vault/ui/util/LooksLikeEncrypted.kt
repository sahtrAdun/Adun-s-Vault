package dot.adun.vault.ui.util

fun String.looksLikeEncrypted(): Boolean {
    if (this.length < 16) return false

    val knownPrefixes = listOf(
        "U2FsdGVkX1",
        "Salted__",
        "\$AES-",
        "ENC[",
        "\u0001\u0000\u0000\u0000"
    )

    if (knownPrefixes.any { this.startsWith(it) }) return true

    if (this.count { it <= '\u001F' } > this.length / 5) return true

    // Исправленный regex для Base64 (экранирован '+' и '=')
    val base64Regex = Regex("^[A-Za-z0-9\\+\\/\\=]{12,}\$")
    if (base64Regex.matches(this) && this.length % 4 == 0) return true

    if (hasHighEntropy()) return true

    return false
}

private fun String.hasHighEntropy(): Boolean {
    val uniqueChars = this.toSet().size
    val ratio = uniqueChars.toDouble() / this.length

    // Если много уникальных символов относительно длины — возможно, случайная/зашифрованная
    return ratio > 0.7 && this.length > 16
}