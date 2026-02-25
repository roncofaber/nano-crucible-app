package gov.lbl.crucible.scanner.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currentApiKey: String?,
    onApiKeySave: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var apiKeyInput by remember { mutableStateOf(currentApiKey ?: "") }
    var isApiKeyVisible by remember { mutableStateOf(false) }
    var showSaveConfirmation by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "API Configuration",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Enter your Crucible API key to access samples and datasets. " +
                        "You can obtain your API key at: https://crucible.lbl.gov/api/v1/user_apikey",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = apiKeyInput,
                onValueChange = { apiKeyInput = it },
                label = { Text("API Key") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.Key, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = { isApiKeyVisible = !isApiKeyVisible }) {
                        Icon(
                            if (isApiKeyVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (isApiKeyVisible) "Hide API key" else "Show API key"
                        )
                    }
                },
                visualTransformation = if (isApiKeyVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                singleLine = true
            )

            Button(
                onClick = {
                    onApiKeySave(apiKeyInput)
                    showSaveConfirmation = true
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = apiKeyInput.isNotBlank()
            ) {
                Text("Save API Key")
            }

            if (showSaveConfirmation) {
                LaunchedEffect(Unit) {
                    kotlinx.coroutines.delay(2000)
                    showSaveConfirmation = false
                }
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = "✓ API key saved successfully",
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Divider()

            Text(
                text = "About",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Crucible Scanner v1.0\n\n" +
                        "Scan QR codes to quickly access sample and dataset information from " +
                        "the Molecular Foundry's Crucible data system.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
