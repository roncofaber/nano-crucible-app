package gov.lbl.crucible.scanner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import gov.lbl.crucible.scanner.ui.home.HomeScreen
import gov.lbl.crucible.scanner.ui.scanner.QRScannerScreen
import gov.lbl.crucible.scanner.ui.settings.SettingsScreen
import gov.lbl.crucible.scanner.ui.viewmodel.ScannerViewModel
import gov.lbl.crucible.scanner.ui.viewmodel.UiState
import gov.lbl.crucible.scanner.ui.detail.ResourceDetailScreen
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Scanner : Screen("scanner")
    object Settings : Screen("settings")
    object Detail : Screen("detail/{mfid}") {
        fun createRoute(mfid: String) = "detail/$mfid"
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    apiKey: String?,
    onApiKeySave: (String) -> Unit,
    viewModel: ScannerViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                onScanClick = {
                    if (apiKey.isNullOrBlank()) {
                        navController.navigate(Screen.Settings.route)
                    } else {
                        viewModel.reset()
                        navController.navigate(Screen.Scanner.route)
                    }
                },
                onManualEntry = { uuid ->
                    if (apiKey.isNullOrBlank()) {
                        navController.navigate(Screen.Settings.route)
                    } else {
                        navController.navigate(Screen.Detail.createRoute(uuid))
                    }
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        composable(Screen.Scanner.route) {
            QRScannerScreen(
                onQRCodeScanned = { code ->
                    navController.navigate(Screen.Detail.createRoute(code))
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                currentApiKey = apiKey,
                onApiKeySave = onApiKeySave,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("mfid") { type = NavType.StringType })
        ) { backStackEntry ->
            val mfid = backStackEntry.arguments?.getString("mfid") ?: ""

            LaunchedEffect(mfid) {
                viewModel.fetchResource(mfid)
            }

            when (val state = uiState) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CircularProgressIndicator()
                            Text("Loading resource...")
                        }
                    }
                }
                is UiState.Success -> {
                    ResourceDetailScreen(
                        resource = state.resource,
                        thumbnails = state.thumbnails,
                        onBack = {
                            navController.popBackStack()
                        },
                        onNavigateToResource = { newMfid ->
                            navController.navigate(Screen.Detail.createRoute(newMfid))
                        },
                        onHome = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = false }
                            }
                        }
                    )
                }
                is UiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "Error",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.error
                            )
                            Text(
                                text = state.message,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Button(onClick = {
                                navController.popBackStack()
                            }) {
                                Text("Go Back")
                            }
                        }
                    }
                }
                is UiState.Idle -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
