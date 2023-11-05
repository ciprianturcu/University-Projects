package com.example.gamevault.model

import android.media.Rating

data class Game(
    val title: String,
    val description: String,
    val genre: String,
    val progress: Float,
    val rating: Float,
    val hoursPlayed: Int,
)
