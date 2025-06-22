package dot.adun.vault.ui.components.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.vault.domain.encryption.CryptoUtil
import dot.adun.vault.domain.encryption.KeyGenerator.generateEncryptionKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstLaunchViewModel @Inject constructor(
    private val dataStore: FirstLaunchDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(FirstLaunchState())
    val state: StateFlow<FirstLaunchState> = _state.asStateFlow()

    fun onIntent(intent: FirstLaunchIntent) {
        when (intent) {
            is FirstLaunchIntent.GenerateKey -> generateKey()
            is FirstLaunchIntent.CopyKeyToClipboard -> copyKey()
            is FirstLaunchIntent.Confirm -> confirm()
        }
    }

    private fun generateKey() {
        val key = generateEncryptionKey()
        val validationCipher = CryptoUtil.generateValidationCipher(key)
        val newState = _state.value.copy(
            encryptionKey = key,
            validationCipher = validationCipher,
            isKeyCopied = false
        )
        _state.update { newState }
    }

    private fun copyKey() {
        _state.value.encryptionKey ?: return
        _state.update {
            it.copy(isKeyCopied = true)
        }
    }

    fun confirm() {
        val key = _state.value.encryptionKey ?: return
        viewModelScope.launch {
            dataStore.setFirstLaunchCompleted(key)
        }
        _state.update { it.copy(isConfirmed = true) }
    }
}

data class FirstLaunchState(
    val encryptionKey: String? = null,
    val validationCipher: String? = null,
    val isKeyCopied: Boolean = false,
    val isConfirmed: Boolean = false
)

sealed class FirstLaunchIntent {
    object GenerateKey : FirstLaunchIntent()
    object CopyKeyToClipboard : FirstLaunchIntent()
    object Confirm : FirstLaunchIntent()
}