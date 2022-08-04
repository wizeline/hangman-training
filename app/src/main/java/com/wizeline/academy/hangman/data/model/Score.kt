package com.wizeline.academy.hangman.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score")
data class Score(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userName: String,
    val score: Int = 0,
    val position: Int = 0
    )