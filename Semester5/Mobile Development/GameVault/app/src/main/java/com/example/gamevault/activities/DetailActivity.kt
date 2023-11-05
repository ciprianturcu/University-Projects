package com.example.gamevault.activities

import android.annotation.SuppressLint
import android.hardware.camera2.params.ColorSpaceTransform
import android.provider.CalendarContract.Colors
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gamevault.model.Game

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GameDetailScreen(
    navController: NavController,
    gameIndex: Int,
    getGame:(Int)-> Game,
    deleteGame:(Int) -> Unit
) {

    var game = getGame(gameIndex)
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            IconButton(onClick = { navController.popBackStack() },
                modifier = Modifier.padding(12.dp),) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back", modifier = Modifier
                    .background(
                        Color(0xFF333437)
                    )
                    .size(25.dp), tint = Color(0xFFFFD232))
            }
        },
        containerColor = Color(0xFF333437),
        contentColor = Color(0xFF212223),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 55.dp)
                    .background(Color(0xFF333437))
            ) {
                // Game Title
                TitleAndGenreColumn(gameTitle = game.title, gameGenre = game.genre)

                //Details Card
                Card(
                    modifier = Modifier.fillMaxSize(),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    ),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF212223))

                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        DescriptionRow(gameDescription = game.description)
                        ProgressAndRatingRow(
                            gameProgress = game.progress.toString(),
                            gameRating = game.rating.toString()
                        )
                        HoursPlayedRow(hoursPlayed = game.hoursPlayed.toString())
                    }

                    ActionButtons( onDeleteClick = {showDeleteDialog = true}, onUpdateClick = {navController.navigate("gameUpdate/${gameIndex}")})

                    if (showDeleteDialog) {
                        DeleteConfirmationDialog(
                            onConfirmDelete = {
                                deleteGame(gameIndex)
                                showDeleteDialog = false
                                navController.popBackStack()
                            },
                            onDismiss = { showDeleteDialog = false }
                        )
                    }
                }

            }
        }
    )
}

@Composable
private fun TitleAndGenreColumn(gameTitle: String, gameGenre: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = gameTitle,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp),
            color = Color(0xFFE0E0E2)
        )
        Text(
            text = gameGenre,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp),
            color = Color(0xFFE0E0E2)
        )
    }
}

@Composable
private fun DescriptionRow(gameDescription: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .fillMaxHeight(0.4f)
    ) {
        Card(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .fillMaxHeight(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF333437))
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFFE0E0E2),
                )
                Text(
                    text = gameDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFE0E0E2)
                )
            }
        }
    }

}

@Composable
private fun ProgressAndRatingRow(gameProgress: String, gameRating: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .fillMaxHeight(0.25f)
    ) {
        Card(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .fillMaxHeight(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF333437))

        ) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Achievements",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFFE0E0E2)
                    )
                Text(
                    text = "${gameProgress}%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFE0E0E2)
                )
            }
        }

        Card(
            modifier = Modifier
                .weight(1f).fillMaxHeight(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF333437))
        ) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Rating",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFFE0E0E2),
                )
                Text(
                    text = gameRating,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFE0E0E2)
                )
            }
        }
    }
}

@Composable
private fun HoursPlayedRow(hoursPlayed: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .fillMaxHeight(0.20f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .fillMaxHeight(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF333437))
        ) {
            Text(
                text = "Hours Played : $hoursPlayed",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(12.dp),
                color = Color(0xFFE0E0E2)
            )
        }

    }
}

@Composable
private fun ActionButtons(onDeleteClick: () -> Unit,
                          onUpdateClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onUpdateClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .border(5.dp, color = Color(0xFFDF921F), shape = CircleShape),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF333437) ,
            )
        ) {
            Text(text = "Update", color = Color(0xFFFFD232))
        }

        Button(
            onClick = onDeleteClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .border(5.dp, color = Color(0xFFB00000), shape = CircleShape),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF333437) ,
            )
        ) {
            Text(text = "Delete", color = Color(0xFFFF1A1A))
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onConfirmDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirm delete") },
        text = { Text("Are you sure you want to delete this game?") },
        confirmButton = {
            TextButton(
                onClick = onConfirmDelete
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("No")
            }
        }
    )
}