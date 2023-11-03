package com.example.gamevault.viewmodels

import android.media.Rating
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.gamevault.model.Game
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {
    private val _gameList = MutableStateFlow(listOf<Game>())
    val gameList = _gameList.asStateFlow()

    fun addGame(game: Game) {
        Log.d("gamelist-before", _gameList.value.toString())
        _gameList.value = _gameList.value + game
        Log.d("gamelist-after", _gameList.value.toString())
    }
    fun deleteGame(gameId:Int) {
        _gameList.value[gameId]
    }

    init {
        addGame(Game(
            "GTA V",
            "Lorem Ipsum",
            "Open World",
            50F,
            Rating.newStarRating(Rating.RATING_5_STARS, 4.5F),
            358
        ))
        addGame(Game(
            "Outlast",
            "Lorem Ipsum",
            "Horror",
            75F,
            Rating.newStarRating(Rating.RATING_5_STARS, 4.5F),
            26
        ))
        addGame(Game(
            "Counter Strike 2",
            "Lorem Ipsum",
            "FPS",
            78.6F,
            Rating.newStarRating(Rating.RATING_5_STARS, 4.5F),
            2453
        ))
        addGame(Game(
            "Rocket League",
            "Lorem Ipsum",
            "Action",
            25F,
            Rating.newStarRating(Rating.RATING_5_STARS, 4.5F),
            372
        ))
        addGame(Game(
            "Minecraft",
            "Lorem Ipsum",
            "Open World",
            80F,
            Rating.newStarRating(Rating.RATING_5_STARS, 4.5F),
            1000
        ))
        addGame(Game(
            "Rainbow 6",
            "Lorem Ipsum",
            "FPS",
            64F,
            Rating.newStarRating(Rating.RATING_5_STARS, 4.5F),
            927
        ))
        addGame(Game(
            "Rainbow 6",
            "Lorem Ipsum",
            "FPS",
            64F,
            Rating.newStarRating(Rating.RATING_5_STARS, 4.5F),
            927
        ))
        addGame(Game(
            "Rainbow 6",
            "Lorem Ipsum",
            "FPS",
            64F,
            Rating.newStarRating(Rating.RATING_5_STARS, 4.5F),
            927
        ))
        addGame(Game(
            "Rainbow 6",
            "Lorem Ipsum",
            "FPS",
            64F,
            Rating.newStarRating(Rating.RATING_5_STARS, 4.5F),
            927
        ))
    }

}