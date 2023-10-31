package com.example.gamevault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gamevault.activities.AddGameScreen
import com.example.gamevault.activities.OverviewScreen
import com.example.gamevault.viewmodels.GameViewModel


class MainActivity : ComponentActivity() {
    val gameViewModel : GameViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            MyApp(gameViewModel = gameViewModel)
        }
    }
}

@Composable
private fun MyApp(gameViewModel: GameViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "overview") {
        composable("overview", arguments = emptyList(), deepLinks = emptyList(), content = {
            OverviewScreen(navController, gameViewModel)
        })
        composable("addGame", arguments = emptyList(), deepLinks = emptyList(), content = {
            AddGameScreen(navController = navController, gameViewModel = gameViewModel)
        })
    }
}


