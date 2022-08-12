package com.wizeline.academy.hangman.data.repository

import com.wizeline.academy.hangman.data.model.MoviesRespose
import com.wizeline.academy.hangman.data.model.Score
import com.wizeline.academy.hangman.data.room.dao.ScoreDao
import com.wizeline.academy.hangman.data.services.MoviesApi
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class HangmanRepository @Inject constructor(
    private val client: MoviesApi,
    private val scoredao: ScoreDao
) {

    // get Movie
    fun getMovie(): Single<MoviesRespose> {

        val randomPage = (1..100).random()
        return client.getMovie(page = randomPage)
    }

    //save score (username)
    fun saveUserName(userName: String): Long {
        return scoredao.insert(Score(userName= userName))
    }

    fun getUserName(userName: String): Score?{
        return scoredao.getUserName(userName)
    }

    fun updateScore(userName: String, score: Int){
        scoredao.updateScore(score, userName)
    }

    fun getScores(): List<Score>{
        return scoredao.getScores()
    }
}