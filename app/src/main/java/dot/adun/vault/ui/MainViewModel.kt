package dot.adun.vault.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dot.adun.vault.R
import dot.adun.vault.data.mappers.mapAll
import dot.adun.vault.data.mappers.toDecrypted
import dot.adun.vault.domain.VaultRepository
import dot.adun.vault.domain.encryption.CipherWrapper
import dot.adun.vault.domain.encryption.CryptoUtil
import dot.adun.vault.domain.entities.DecryptedSite
import dot.adun.vault.domain.entities.Email
import dot.adun.vault.domain.entities.EncryptedString
import dot.adun.vault.domain.entities.Site
import dot.adun.vault.ui.components.onboarding.FirstLaunchDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataRepository: VaultRepository,
    private val dataStore: FirstLaunchDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    private var currentKey: String? = null

    init {
        viewModelScope.launch {
            dataRepository.getAllEmails().collect { emails ->
                _state.update {
                    it.copy(
                        init = false,
                        emails = emails,
                        actualEmails = emails.map { it.toDecrypted() }
                    )
                }
                if (!currentKey.isNullOrEmpty()) {
                    decryptAllEmails(currentKey!!)
                }
            }
        }
    }

    fun onIntent(intent: MainIntents) {
        when (intent) {
            is MainIntents.UpdateSearchText -> updateSearchText(intent.text)
            is MainIntents.UpdateKeyText -> updateKeyText(intent.key)
            is MainIntents.ValidateKey -> validateKeyAndDecryptEmails()
            is MainIntents.AddNewEmail -> addEncryptedEmail(intent.email)
            is MainIntents.SearchModeChange -> onSearchModeChange()
            is MainIntents.AddNewSiteTo -> addEncryptedSiteToEmail(intent.emailId, intent.site)
            is MainIntents.EmitSnackMessage -> emitSnackMessage(intent.message, intent.type)
            is MainIntents.DeleteEmail -> deleteEmail(intent.id)
            is MainIntents.UpdateEmail -> updateEmail(intent.id)
        }
    }

    private fun onSearchModeChange() {
        viewModelScope.launch {
            _state.update {
                it.copy(searchMode = !it.searchMode)
            }
        }
    }

    private fun updateSearchText(text: String) {
        _state.update {
            it.copy(searchText = text)
        }
        // Здесь можно запустить фильтрацию или новый запрос
    }

    private fun updateKeyText(key: String?) {
        _state.update {
            it.copy(keyText = key)
        }
    }

    private fun validateKeyAndDecryptEmails() {
        val key = _state.value.keyText ?: return
        viewModelScope.launch {
            val cipher = dataStore.validationCipher.first()
            if (CryptoUtil.validateKey(key, cipher)) {
                currentKey = key
                decryptAllEmails(key)
                _state.update {
                    it.copy(isKeyValid = true)
                }
            } else {
                _state.update { it.copy(error = it.keyText) }
                emitSnackMessage(
                    message = context.getString(R.string.error_key_invalid),
                    type = SnackMessageType.Error
                )
            }
        }
    }

    private fun decryptAllEmails(key: String) {
        val cipher = CipherWrapper(key)
        val decryptedEmails = mutableMapOf<String, String>()
        val decryptedPasswords = mutableMapOf<String, String>()

        _state.value.emails.forEach { email ->
            cipher.decrypt(email.encryptedEmail.value)?.let { decryptedEmail ->
                decryptedEmails[email.id] = decryptedEmail
                email.sites.forEach { site ->
                    cipher.decrypt(site.encryptedPassword.value)?.let { password ->
                        decryptedPasswords[site.id] = password
                    }
                }
            }
        }

        val decryptedEmailsList = mapAll(
            emails = _state.value.emails,
            decryptedEmails,
            decryptedPasswords
        )

        _state.update {
            it.copy(
                decryptedEmails = decryptedEmails,
                decryptedPasswords = decryptedPasswords,
                actualEmails = decryptedEmailsList
            )
        }
    }

    private fun addEncryptedSiteToEmail(emailId: String, site: DecryptedSite) {
        val key = currentKey ?: run {
            emitSnackMessage(
                message = context.getString(R.string.error_key),
                type = SnackMessageType.Error
            )
            return
        }

        val cipher = CipherWrapper(key)
        val encryptedPassword = cipher.encrypt(site.password)

        val newSite = Site(
            id = UUID.randomUUID().toString(),
            name = site.name,
            url = site.url,
            encryptedPassword = EncryptedString(encryptedPassword)
        )

        viewModelScope.launch {
            dataRepository.addSiteTo(emailId, newSite)
        }
    }

    private fun addEncryptedEmail(email: String) {
        val key = currentKey ?: run {
            emitSnackMessage(
                message = context.getString(R.string.error_key),
                type = SnackMessageType.Error
            )
            return
        }

        val cipher = CipherWrapper(key)
        val encryptedEmail = cipher.encrypt(email)

        val newEmail = Email(
            id = UUID.randomUUID().toString(),
            encryptedEmail = EncryptedString(encryptedEmail),
            sites = emptyList()
        )

        viewModelScope.launch {
            dataRepository.addEmail(newEmail)
        }
    }

    private fun updateEmail(emailId: String) {
        val email = _state.value.emails.find { it.id == emailId} ?: return
        viewModelScope.launch { dataRepository.updateEmail(email) }
    }

    private fun deleteEmail(emailId: String) {
        val email = _state.value.emails.find { it.id == emailId} ?: return
        viewModelScope.launch { dataRepository.deleteEmail(email) }
    }

    private fun emitSnackMessage(message: String, type: SnackMessageType = SnackMessageType.Error) {
        viewModelScope.launch {
            _state.update { it.copy(snackMessage = SnackMessage(message, type = type)) }
            delay(4000)
            _state.update { it.copy(snackMessage = null) }
        }
    }
}