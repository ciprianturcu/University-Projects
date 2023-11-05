package com.example.gamevault.activities

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gamevault.customui.CustomProgressBar
import com.example.gamevault.model.Game
import com.example.gamevault.viewmodels.GameViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(navController: NavController, gameViewModel: GameViewModel = viewModel()) {
    val games by gameViewModel.gameList.collectAsState()

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(Color(0xFF212223)),
                contentAlignment = Alignment.Center
            ) {
                Text("Game Vault", fontSize = 34.sp, color = Color(0xFFE0E0E2))
            }
        },
        containerColor = Color(0xFF212223),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("addGame")

                },
                contentColor = Color(0xFFFFD232),
                containerColor = Color(0xFF47494F),
                content = {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null,
                        Modifier.size(50.dp),

                    )
                },
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(top = 80.dp)
                    .padding(horizontal = 10.dp)
                    .background(Color(0xFF212223))

            ) {
                CreateGameList(navController = navController, games = games)
            }
        },
    )
}

@Composable
private fun CreateGameList(
    modifier: Modifier = Modifier,
    navController: NavController,
    games: List<Game>
) {
    LazyColumn(
        modifier = modifier
            .padding(vertical = 8.dp)
    ) {
        itemsIndexed(items = games) { index, game ->
            CreateGameCard(navController = navController, game = game, gameIndex = index)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateGameCard(navController: NavController, game: Game, gameIndex: Int) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF35363A)
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        onClick = { navController.navigate("gameDetails/$gameIndex") }
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
                .padding(8.dp)
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = game.title,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFFE0E0E2)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left-aligned Genre
                Text(text = game.genre, color = Color(0xFFE0E0E2))

                // Right-aligned Hours Played
                Text(text = "${game.hoursPlayed} hours", color = Color(0xFFE0E0E2))

                Text(text = "${game.progress} %", color = Color(0xFFE0E0E2))
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                CustomProgressBar(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(4.dp))
                        .height(14.dp),
                    backgroundColor = Color(0xFF212223),
                    foregroundColor = Brush.horizontalGradient(
                        listOf(
                            Color(0xFFDF921F),
                            Color(0xFFFFD232)
                        )
                    ),
                    percent = game.progress,
                    isShownText = false
                )
            }
        }
    }
}