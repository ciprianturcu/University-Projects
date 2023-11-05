package com.example.gamevault.viewmodels

import android.media.Rating
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.gamevault.model.Game
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {
    private val _gameList = MutableStateFlow(listOf<Game>())
    val gameList = _gameList.asStateFlow()

    fun addGame(game: Game) {
        _gameList.value = _gameList.value + game
    }

    fun updateGame(index: Int, newGame: Game) {
        val updatedList = gameList.value.toMutableList()
        updatedList[index] = newGame
        _gameList.value = updatedList
    }

    fun deleteGame(index: Int) {
        val gameList = gameList.value.toMutableList()
        gameList.removeAt(index)
        _gameList.value = gameList
    }

    fun getGameByIndex(index: Int): Game {
        return gameList.value.getOrNull(index) ?: Game(
            title = "",
            description = "",
            genre = "",
            progress = 0F,
            rating = 0F,
            hoursPlayed = 0
        )
    }

    init {
        addGame(Game(
            "GTA V",
            "Lorem Ipsum",
            "Open World",
            50F,
            4.5F,
            358
        ))
        addGame(Game(
            "Outlast",
            "Lorem Ipsum",
            "Horror",
            75F,
            4.5F,
            26
        ))
        addGame(Game(
            "Counter Strike 2",
            "Lorem Ipsum",
            "FPS",
            78.6F,
            4.5F,
            2453
        ))
        addGame(Game(
            "Rocket League",
            "Lorem Ipsum",
            "Action",
            25F,
            4.5F,
            372
        ))
        addGame(Game(
            "Minecraft",
            "Lorem Ipsum",
            "Open World",
            80F,
            4.5F,
            1000
        ))
        addGame(Game(
            "Rainbow 6",
            "Lorem Ipsum",
            "FPS",
            64F,
            4.5F,
            927
        ))
        addGame(Game(
            "Rainbow 6",
            "Lorem Ipsum",
            "FPS",
            64F,
            4.5F,
            927
        ))
        addGame(Game(
            "Rainbow 6",
            "Lorem Ipsum",
            "FPS",
            64F,
            4.5F,
            927
        ))
        addGame(Game(
            "Rainbow 6",
            "Lorem Ipsum",
            "FPS",
            64F,
            4.5F,
            927
        ))
    }

}