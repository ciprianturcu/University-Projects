package com.example.gamevault.repository

import android.media.Rating
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.gamevault.model.Game

class GameRepository {
    private var games = mutableMapOf<Int, Game>()
    private var currentId = 0

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

    fun getAllGames(): List<Game> {
        return games.values.toList()
    }

    fun getGameById(id:Int): Game? {
        return games[id]
    }

    fun addGame(game: Game): Int {
        currentId++;
        games[currentId] = game
        Log.d("add", game.toString())
        return currentId
    }

    fun updateGame(id:Int, game: Game) : Boolean {
        val existingGame = games[id]
        if(existingGame!=null) {
            games[id] = game
            return true
        }
        return false
    }

    fun deleteGame(id: Int) : Boolean {
        val removedGame = games.remove(id)
        return removedGame != null
    }
}

@Composable
fun rememberGameRepository(): GameRepository {
    return remember { GameRepository() }
}
