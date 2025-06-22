package dot.adun.vault.ui

import dot.adun.vault.domain.entities.DecryptedSite

sealed class MainIntents {
    object ValidateKey : MainIntents()
    object SearchModeChange : MainIntents()
    data class UpdateEmail(val id: String) : MainIntents()
    data class DeleteEmail(val id: String) : MainIntents()
    data class AddNewEmail(val email: String) : MainIntents()
    data class UpdateKeyText(val key: String?) : MainIntents()
    data class UpdateSearchText(val text: String) : MainIntents()
    data class AddNewSiteTo(val emailId: String, val site: DecryptedSite) : MainIntents()
    data class EmitSnackMessage(val message: String, val type: SnackMessageType) : MainIntents()
}

class MainIntentsWrapper(
    private val onIntent: (MainIntents) -> Unit
) {
    fun validateKey() = onIntent(MainIntents.ValidateKey)
    fun searchModeChange() = onIntent(MainIntents.SearchModeChange)
    fun editEmail(id: String) = onIntent(MainIntents.UpdateEmail(id))
    fun deleteEmail(id: String) = onIntent(MainIntents.DeleteEmail(id))
    fun addNewEmail(email: String) = onIntent(MainIntents.AddNewEmail(email))
    fun updateKeyText(key: String?) = onIntent(MainIntents.UpdateKeyText(key))
    fun updateSearchText(text: String) = onIntent(MainIntents.UpdateSearchText(text))
    fun addNewSiteTo(emailId: String, site: DecryptedSite) = onIntent(MainIntents.AddNewSiteTo(emailId, site))
    fun emitSnackMessage(message: String, type: SnackMessageType) = onIntent(MainIntents.EmitSnackMessage(message, type))
}