package gov.lbl.crucible.scanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import gov.lbl.crucible.scanner.data.api.ApiClient
import gov.lbl.crucible.scanner.data.preferences.PreferencesManager
import gov.lbl.crucible.scanner.ui.navigation.NavGraph
import gov.lbl.crucible.scanner.ui.theme.CrucibleScannerTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferencesManager = PreferencesManager(this)

        setContent {
            CrucibleScannerTheme {
                val navController = rememberNavController()
                val apiKey by preferencesManager.apiKey.collectAsState(initial = null)
                val scope = rememberCoroutineScope()

                // Set API key in client when it changes
                apiKey?.let { key ->
                    ApiClient.setApiKey(key)
                }

                NavGraph(
                    navController = navController,
                    apiKey = apiKey,
                    onApiKeySave = { key ->
                        scope.launch {
                            preferencesManager.saveApiKey(key)
                            ApiClient.setApiKey(key)
                        }
                    }
                )
            }
        }
    }
}
