package com.example.gamevault.activities

import android.annotation.SuppressLint
import android.media.Rating
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gamevault.model.Game
import com.example.gamevault.ui.theme.GameVaultTheme
import com.example.gamevault.viewmodels.GameViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGameScreen(navController: NavController, gameViewModel: GameViewModel) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var progress by remember { mutableFloatStateOf(0f) }
    var rating by remember { mutableFloatStateOf(0f) }
    var hoursPlayed by remember { mutableIntStateOf(0) }

    // Define error states for each input field
    var titleError by remember { mutableStateOf("") }
    var descriptionError by remember { mutableStateOf("") }
    var genreError by remember { mutableStateOf("") }
    var progressError by remember { mutableStateOf("") }
    var ratingError by remember { mutableStateOf("") }
    var hoursPlayedError by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Box(modifier = Modifier.background(Color(0xFF212223))) {
                Text(
                    text = "Add Game", style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.height(75.dp).padding(20.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color(0xFFE0E0E2)
                )
                IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.padding(10.dp)) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .background(
                                Color(0xFF212223)
                            )
                            .size(50.dp),
                        tint = Color(0xFFFFD232)
                    )
                }
            }
        },
        containerColor = Color(0xFF212223),
        content = {
            Column(
                modifier = Modifier.padding(80.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                AddGameInputField("Title", title) { title = it }
                if (titleError.isNotEmpty()) {
                    Text(text = titleError, color = Color.Red)
                }
                AddGameInputField("Description", description) { description = it }
                if (descriptionError.isNotEmpty()) {
                    Text(text = descriptionError, color = Color.Red)
                }
                AddGameInputField("Genre", genre) { genre = it }
                if (genreError.isNotEmpty()) {
                    Text(text = genreError, color = Color.Red)
                }
                AddGameInputField("Progress", progress.toString()) {
                    progress = it.toFloatOrNull() ?: 0f
                }
                if (progressError.isNotEmpty()) {
                    Text(text = progressError, color = Color.Red)
                }
                AddGameInputField("Rating", rating.toString()) { rating = it.toFloatOrNull() ?: 0f }
                if (ratingError.isNotEmpty()) {
                    Text(text = ratingError, color = Color.Red)
                }
                AddGameInputField("Hours Played", hoursPlayed.toString()) {
                    hoursPlayed = it.toIntOrNull() ?: 0
                }
                if (hoursPlayedError.isNotEmpty()) {
                    Text(text = hoursPlayedError, color = Color.Red)
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        val isTitleValid = title.isNotBlank()
                        val isDescriptionValid = description.isNotBlank()
                        val isGenreValid = genre.isNotBlank()
                        val isProgressValid = progress in 0.0..100.0
                        val isRatingValid = rating in 0.0..5.0
                        val isHoursPlayedValid = hoursPlayed < Int.MAX_VALUE
                        if (isTitleValid && isDescriptionValid && isGenreValid && isProgressValid && isRatingValid && isHoursPlayedValid) {
                            val game = Game(
                                title,
                                description,
                                genre,
                                progress,
                                rating,
                                hoursPlayed
                            )
                            gameViewModel.addGame(game)
                            navController.popBackStack()
                        } else {
                            titleError = if (!isTitleValid) "Title is required" else ""
                            descriptionError =
                                if (!isDescriptionValid) "Description is required" else ""
                            genreError = if (!isGenreValid) "Genre is required" else ""
                            progressError =
                                if (!isProgressValid) "Progress should be between 0-100" else ""
                            ratingError =
                                if (!isRatingValid) "Rating should be between 0-5" else ""
                            hoursPlayedError =
                                if (!isHoursPlayedValid) "Hours played cannot be this large" else ""
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(5.dp, color = Color(0xFFDF921F), shape = CircleShape),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF333437),
                    )
                ) {
                    Text(text = "Add Game", color = Color(0xFFFFD232))
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

    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        label = { Text(text = label, color = Color(0xFFE0E0E2)) },
        modifier = Modifier
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            }
            .padding(bottom = 16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color(0xFFE0E0E2),
            focusedLabelColor = Color(0xFFDF921F),
            unfocusedLabelColor = Color(0xFFDF921F),
            unfocusedBorderColor = Color(0xFFDF921F),
            focusedBorderColor = Color(0xFFDF921F)
        )
    )


    if (!isFocused) {
        LocalSoftwareKeyboardController.current?.hide()
    }
}