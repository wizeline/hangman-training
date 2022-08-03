package com.wizeline.academy.hangman.util

import com.wizeline.academy.hangman.data.model.Letter


fun String?.isValid(): Boolean = !this.isNullOrEmpty()

fun Long.timerFormat(): String {
    val s: Long = this % 60
    val m: Long = this / 60 % 60
    return String.format("%02d:%02d", m, s)
}

fun Array<String>.toGameAdapter(): List<Letter>{
    val wordsList = mutableListOf<Letter>()
    this.map {
        wordsList.add(
            Letter(it,false)
        )
    }
    return wordsList
}

fun List<Letter>.setVisibleLetter(letter: String): List<Letter>{
    val wordsList = mutableListOf<Letter>()

    this.map {
        wordsList.add(
            Letter(it.letter,it.letter.uppercase() == letter.uppercase() || it.isVisible)
        )
    }
    return wordsList
}

fun List<Letter>.hasLetter(letter: String): Boolean =  this.any { it.letter.uppercase() == letter.uppercase() }
fun List<Letter>.isVisible(letter: String): Boolean =  this.any { it.letter.uppercase() == letter.uppercase() && it.isVisible }
fun List<Letter>.isWordVisible(): Boolean = this.all { it.letter != "" && it.isVisible }