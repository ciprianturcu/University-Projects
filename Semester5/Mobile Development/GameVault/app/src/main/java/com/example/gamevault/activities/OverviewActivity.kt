package com.example.gamevault.activities

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gamevault.customui.CustomProgressBar
import com.example.gamevault.model.Game
import com.example.gamevault.viewmodels.GameViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(navController: NavController,gameViewModel : GameViewModel = viewModel()) {
    val games by gameViewModel.gameList.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(50.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,

                        ) {
                        Text("Game Vault")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("addGame")
                },
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(top = 55.dp)
                    .padding(horizontal = 10.dp)
            ) {
                CreateGameList(navController = navController,games = games)
            }
        }
    )
}

@Composable
private fun CreateGameList(modifier: Modifier = Modifier, navController: NavController, games: List<Game>) {
    LazyColumn(
        modifier = modifier
            .padding(vertical = 4.dp)
    ) {
        itemsIndexed(items = games) { index , game ->
            CreateGameCard(navController = navController,game = game, gameIndex = index)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateGameCard(navController: NavController, game: Game, gameIndex:Int) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        onClick = { }
    ) {
        GameCardContent(game)
    }
}

@Composable
private fun GameCardContent(game: Game) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        )
        {
            Text(text = game.title)
            Text(text = game.genre)
            Text(text = game.hoursPlayed.toString())
            CustomProgressBar(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(4.dp))
                    .height(14.dp),
                width = 300.dp,
                backgroundColor = Color.Gray,
                foregroundColor = Brush.horizontalGradient(
                    listOf(
                        Color(0xFF74A13A),
                        Color(0xFF184614)
                    )
                ),
                percent = game.progress,
                isShownText = true
            )
        }
    }
}