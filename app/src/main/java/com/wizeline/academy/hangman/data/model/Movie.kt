package com.wizeline.academy.hangman.data.model

import com.google.gson.annotations.SerializedName


data class MoviesRespose(
    @SerializedName("results") val result: List<Movie>,
    @SerializedName("status_message") val status: String,
    @SerializedName("status_code") val code: String
)

data class Movie (val title: String, val original_language: String = "")