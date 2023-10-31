package com.example.gamevault.activities

import android.annotation.SuppressLint
import android.media.Rating
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gamevault.model.Game
import com.example.gamevault.ui.theme.GameVaultTheme
import com.example.gamevault.viewmodels.GameViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGameScreen(navController: NavController,gameViewModel: GameViewModel) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var progress by remember { mutableFloatStateOf(0f) }
    var rating by remember { mutableStateOf("") }
    var hoursPlayed by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Game") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Add Game",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                AddGameInputField("Title", title) { title = it }
                AddGameInputField("Description", description) { description = it }
                AddGameInputField("Genre", genre) { genre = it }
                AddGameInputField("Progress", progress.toString()) { progress = it.toFloat() }
                AddGameInputField("Rating", rating) { rating = it }
                AddGameInputField("Hours Played", hoursPlayed.toString()) {
                    hoursPlayed = it.toInt()
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        val game = Game(
                            title,
                            description,
                            genre,
                            progress,
                            Rating.newStarRating(Rating.RATING_5_STARS, rating.toFloat()),
                            hoursPlayed
                        )
                        gameViewModel.addGame(game)
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Add Game")
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddGameInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(value = value, onValueChange = { newValue ->
        onValueChange(newValue)
    },
        label = { Text(text = label) },
        modifier = Modifier
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            }
            .padding(bottom = 16.dp)
    )

    if (!isFocused) {
        LocalSoftwareKeyboardController.current?.hide()
    }
}