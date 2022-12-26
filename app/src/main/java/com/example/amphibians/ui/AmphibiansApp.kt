package com.example.amphibians.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.amphibians.ui.screens.HomeScreen
import com.example.amphibians.R
import com.example.amphibians.ui.screens.AmphibiansViewModel
import com.example.amphibians.ui.screens.DescriptionScreen

enum class AmphibiansScreen() {
    Start,
    Description
}

@Composable
fun AmphibiansApp(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val amphibiansViewModel: AmphibiansViewModel =
        viewModel(factory = AmphibiansViewModel.Factory)
    val amphibianUiState by amphibiansViewModel.uiState.collectAsState()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(
            title = { Text(stringResource(R.string.app_name)) },
            actions = {
                IconButton(onClick = {
                    amphibiansViewModel.getAmphibians()
                }) {
                    Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Refresh")
                }
            }
        ) }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colors.background
        ) {
            NavHost(
                navController = navController,
                startDestination = AmphibiansScreen.Start.name
            ) {
                composable(route = AmphibiansScreen.Start.name) {
                    HomeScreen(
                        amphibiansUiState = amphibiansViewModel.amphibiansUiState,
                        onDescriptionButtonClicked = {
                            amphibiansViewModel.updateChoosedAmphibian(it)
                            navController.navigate(AmphibiansScreen.Description.name)
                        },
                        retryAction = amphibiansViewModel::getAmphibians
                    )
                }
                composable(route = AmphibiansScreen.Description.name) {
                    DescriptionScreen(
                        amphibian = amphibianUiState.choosedAmphibian,
                        onBackButtonClicked = {
                            navController.popBackStack(
                                AmphibiansScreen.Start.name,
                                inclusive = false
                            )
                        }
                    )
                }
            }
        }
    }
}
