package com.wizeline.academy.hangman.data.room.dao

import androidx.room.*
import com.wizeline.academy.hangman.data.model.Score

@Dao
interface ScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: Score): Long

    @Update
    fun update(entity: Score)

    @Query("DELETE FROM score")
    fun delete()

    @Query("SELECT * FROM score ORDER BY score DESC LIMIT 10")
    fun getScores(): List<Score>

    @Query("UPDATE  score SET score=:score WHERE userName = :userName ")
    fun updateScore(score: Int, userName: String)

    @Query("SELECT * FROM score WHERE userName = :userName")
    fun getUserName(userName: String): Score?
}