package dot.adun.vault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import dot.adun.vault.ui.MainScreen
import dot.adun.vault.ui.MainViewModel
import dot.adun.vault.ui.components.onboarding.FirstLaunchDataStore
import dot.adun.vault.ui.components.onboarding.FirstLaunchDialog
import dot.adun.vault.ui.theme.AdunsVaultTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdunsVaultTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    val isFirstLaunch = FirstLaunchDataStore(context).isUserFirstLaunch.collectAsState(false)

                    MainScreen(viewModel = hiltViewModel<MainViewModel>())
                    if (isFirstLaunch.value == true) {
                        FirstLaunchDialog { key ->}
                    }
                }
            }
        }
    }
}
