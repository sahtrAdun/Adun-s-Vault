package dot.adun.vault.ui.components.onboarding

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dot.adun.vault.domain.encryption.CryptoUtil
import kotlinx.coroutines.flow.map

val Context.firstLaunchDataStore: DataStore<Preferences> by preferencesDataStore(name = "first_launch")

class FirstLaunchDataStore(private val context: Context) {

    private object PreferencesKey {
        val isFirstLaunch = booleanPreferencesKey("is_first_launch")
        val validationCipher = stringPreferencesKey("validation_cipher")
    }

    val isUserFirstLaunch = context.firstLaunchDataStore.data.map { preferences ->
        preferences[PreferencesKey.isFirstLaunch] ?: true
    }

    suspend fun setFirstLaunchCompleted(key: String) {
        val validationCipher = CryptoUtil.generateValidationCipher(key)
        context.firstLaunchDataStore.edit { preferences ->
            preferences[PreferencesKey.isFirstLaunch] = false
            preferences[PreferencesKey.validationCipher] = validationCipher
        }
    }

    val validationCipher = context.firstLaunchDataStore.data.map { preferences ->
        preferences[PreferencesKey.validationCipher] ?: ""
    }
}