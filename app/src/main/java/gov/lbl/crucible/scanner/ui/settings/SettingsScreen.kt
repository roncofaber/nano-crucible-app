package gov.lbl.crucible.scanner.ui.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currentApiKey: String?,
    currentApiBaseUrl: String,
    currentGraphExplorerUrl: String,
    currentThemeMode: String,
    currentAccentColor: String,
    onApiKeySave: (String) -> Unit,
    onApiBaseUrlSave: (String) -> Unit,
    onGraphExplorerUrlSave: (String) -> Unit,
    onThemeModeSave: (String) -> Unit,
    onAccentColorSave: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var apiKeyInput by remember { mutableStateOf(currentApiKey ?: "") }
    var apiBaseUrlInput by remember { mutableStateOf(currentApiBaseUrl) }
    var graphExplorerUrlInput by remember { mutableStateOf(currentGraphExplorerUrl) }
    var themeModeInput by remember { mutableStateOf(currentThemeMode) }
    var accentColorInput by remember { mutableStateOf(currentAccentColor) }
    var isApiKeyVisible by remember { mutableStateOf(false) }
    var showColorPicker by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onApiKeySave(apiKeyInput)
                    onApiBaseUrlSave(apiBaseUrlInput)
                    onGraphExplorerUrlSave(graphExplorerUrlInput)
                    onThemeModeSave(themeModeInput)
                    onAccentColorSave(accentColorInput)
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Settings saved successfully",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            ) {
                Icon(Icons.Default.Save, contentDescription = "Save All Settings")
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .padding(bottom = 80.dp), // Extra padding for FAB
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "API Configuration",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Enter your Crucible API key to access samples and datasets.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Clickable card to get API key
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://crucible.lbl.gov/api/v1/user_apikey"))
                        context.startActivity(intent)
                    },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.VpnKey,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Column {
                            Text(
                                text = "Get Your API Key",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Text(
                                text = "crucible.lbl.gov/api/v1/user_apikey",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                            )
                        }
                    }
                    Icon(
                        Icons.Default.OpenInNew,
                        contentDescription = "Open link",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

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

            HorizontalDivider()

            // Appearance Section
            Text(
                text = "Appearance",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Customize the look and feel of the app.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Theme Mode Selection
            Card {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.DarkMode,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Theme",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = themeModeInput == "system",
                            onClick = { themeModeInput = "system" },
                            label = { Text("System") },
                            leadingIcon = if (themeModeInput == "system") {
                                { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp)) }
                            } else null,
                            modifier = Modifier.weight(1f)
                        )
                        FilterChip(
                            selected = themeModeInput == "light",
                            onClick = { themeModeInput = "light" },
                            label = { Text("Light") },
                            leadingIcon = if (themeModeInput == "light") {
                                { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp)) }
                            } else null,
                            modifier = Modifier.weight(1f)
                        )
                        FilterChip(
                            selected = themeModeInput == "dark",
                            onClick = { themeModeInput = "dark" },
                            label = { Text("Dark") },
                            leadingIcon = if (themeModeInput == "dark") {
                                { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp)) }
                            } else null,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Accent Color Selection
            Card(
                modifier = Modifier.clickable { showColorPicker = true }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Palette,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Accent Color",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Color preview box
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    getColorFromName(accentColorInput),
                                    shape = MaterialTheme.shapes.small
                                )
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.outline,
                                    MaterialTheme.shapes.small
                                )
                        )
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = "Choose color"
                        )
                    }
                }
            }

            HorizontalDivider()

            Spacer(modifier = Modifier.height(24.dp))

            // API Endpoints Section
            Text(
                text = "API Endpoints",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Configure the Crucible API and Graph Explorer URLs. Leave as default unless you're using a custom deployment.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = apiBaseUrlInput,
                onValueChange = { apiBaseUrlInput = it },
                label = { Text("API Base URL") },
                placeholder = { Text("https://crucible.lbl.gov/api/v1/") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.Cloud, contentDescription = null)
                },
                singleLine = true,
                supportingText = {
                    Text(
                        text = "REST API endpoint for fetching resources",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = graphExplorerUrlInput,
                onValueChange = { graphExplorerUrlInput = it },
                label = { Text("Graph Explorer URL") },
                placeholder = { Text("https://crucible-graph-explorer-...") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.Language, contentDescription = null)
                },
                singleLine = true,
                supportingText = {
                    Text(
                        text = "Web interface for viewing entity graphs",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            )

            HorizontalDivider()

            // About Section
            AboutSection()
        }
    }

    // Color Picker Dialog
    if (showColorPicker) {
        ColorPickerDialog(
            currentColor = accentColorInput,
            onColorSelected = { color ->
                accentColorInput = color
                showColorPicker = false
            },
            onDismiss = { showColorPicker = false }
        )
    }
}

@Composable
private fun AboutSection() {
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "About",
            style = MaterialTheme.typography.titleLarge
        )

        // App Info Card
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Crucible Lens",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Text(
                    text = "Version 1.0.0",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                Text(
                    text = "Scan QR codes to quickly access sample and dataset information from the Molecular Foundry's Crucible data system.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Developer Card
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/roncofaber"))
                context.startActivity(intent)
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Column {
                        Text(
                            text = "Developer",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                        )
                        Text(
                            text = "@roncofaber",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
                Icon(
                    Icons.Default.OpenInNew,
                    contentDescription = "Open GitHub profile",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // GitHub Repository Card
        Card(
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/roncofaber/nano-crucible-app"))
                context.startActivity(intent)
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Code,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Column {
                        Text(
                            text = "Source Code",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "github.com/roncofaber/nano-crucible-app",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Icon(
                    Icons.Default.OpenInNew,
                    contentDescription = "Open repository",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Molecular Foundry Card
        Card(
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://foundry.lbl.gov/"))
                context.startActivity(intent)
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Business,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Column {
                        Text(
                            text = "Molecular Foundry",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Lawrence Berkeley National Laboratory",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Icon(
                    Icons.Default.OpenInNew,
                    contentDescription = "Visit website",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // License
        Text(
            text = "Licensed under BSD-3-Clause",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun ColorPickerDialog(
    currentColor: String,
    onColorSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val colors = listOf(
        "blue" to Color(0xFF1976D2),
        "purple" to Color(0xFF9C27B0),
        "green" to Color(0xFF388E3C),
        "orange" to Color(0xFFF57C00),
        "red" to Color(0xFFD32F2F),
        "teal" to Color(0xFF00796B),
        "pink" to Color(0xFFE91E63),
        "indigo" to Color(0xFF3F51B5),
        "amber" to Color(0xFFFFA000),
        "lime" to Color(0xFFAFB42B),
        "cyan" to Color(0xFF0097A7),
        "brown" to Color(0xFF5D4037)
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Choose Accent Color",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                colors.chunked(4).forEach { rowColors ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowColors.forEach { (name, color) ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .background(color, shape = MaterialTheme.shapes.medium)
                                    .border(
                                        width = if (currentColor == name) 3.dp else 1.dp,
                                        color = if (currentColor == name)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            MaterialTheme.colorScheme.outline,
                                        shape = MaterialTheme.shapes.medium
                                    )
                                    .clickable { onColorSelected(name) },
                                contentAlignment = Alignment.Center
                            ) {
                                if (currentColor == name) {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = "Selected",
                                        tint = Color.White,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

private fun getColorFromName(colorName: String): Color {
    return when (colorName.lowercase()) {
        "blue" -> Color(0xFF1976D2)
        "purple" -> Color(0xFF9C27B0)
        "green" -> Color(0xFF388E3C)
        "orange" -> Color(0xFFF57C00)
        "red" -> Color(0xFFD32F2F)
        "teal" -> Color(0xFF00796B)
        "pink" -> Color(0xFFE91E63)
        "indigo" -> Color(0xFF3F51B5)
        "amber" -> Color(0xFFFFA000)
        "lime" -> Color(0xFFAFB42B)
        "cyan" -> Color(0xFF0097A7)
        "brown" -> Color(0xFF5D4037)
        else -> Color(0xFF1976D2) // Default to blue
    }
}
