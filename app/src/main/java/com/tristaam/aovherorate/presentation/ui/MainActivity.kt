package com.tristaam.aovherorate.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tristaam.aovherorate.presentation.ui.home.HomeScreen
import com.tristaam.aovherorate.presentation.ui.theme.AoVHeroRateTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            mainViewModel.uiState.value.isLoading
        }
        enableEdgeToEdge()
        setContent {
            AoVHeroRateTheme {
                val navHostController = rememberNavController()
                AoVHeroRateNavHost(
                    navHostController = navHostController,
                    startDestination = Screen.Home.route
                )
            }
        }
    }
}

@Composable
fun AoVHeroRateNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: String
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
    }
}

sealed class Screen(val route: String) {
    data object Home : Screen("home")
}