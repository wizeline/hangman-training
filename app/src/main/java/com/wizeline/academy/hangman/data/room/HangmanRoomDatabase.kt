package com.wizeline.academy.hangman.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wizeline.academy.hangman.data.model.Score
import com.wizeline.academy.hangman.data.room.dao.ScoreDao
import com.wizeline.academy.hangman.util.DATABASE_NAME


@Database(
    entities = [Score::class],
    version = 1,
    exportSchema = false
)


abstract class HangmanRoomDatabase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao

    companion object {
        @Volatile
        private var INSTANCE: HangmanRoomDatabase? = null

        fun getDatabase(context: Context): HangmanRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HangmanRoomDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}