package com.wizeline.academy.hangman.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wizeline.academy.hangman.data.model.Movie
import com.wizeline.academy.hangman.data.repository.HangmanRepository
import com.wizeline.academy.hangman.util.MAX_WORDS
import com.wizeline.academy.hangman.util.TIME_SECONDS
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import java.time.LocalDateTime
import java.time.ZoneOffset


import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val hangmanRepository: HangmanRepository
) : ViewModel() {

    private val _movie = MutableLiveData<Array<String>?>()
    val movie : MutableLiveData<Array<String>?>
        get() = _movie

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _timer = MutableLiveData<Long>(0)
    val timer: LiveData<Long>
        get() = _timer

    private val _endGame = MutableLiveData<Boolean>()
    val endGame: LiveData<Boolean>
        get() = _endGame

    private val _wordCounter = MutableLiveData<Int>(0)
    val wordCounter: LiveData<Int>
        get() = _wordCounter

    private val currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
    private val expireDate = LocalDateTime.now().plusSeconds(TIME_SECONDS).toEpochSecond(ZoneOffset.UTC)


    private val compositeDisposable = CompositeDisposable()


    fun getMovie(){
        hangmanRepository.getMovie()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { it ->
                    val movies =  it.result.filter { movies -> movies.original_language.contains("en") }
                    val re = "[^A-Za-z0-9 ]".toRegex()
                    _wordCounter.value = (_wordCounter.value?.toInt() ?: 0) + 1

                    if((_wordCounter.value?.toInt() ?: 0) > MAX_WORDS){
                        endGame(true)
                        val emptyArray : Array<String> = emptyArray()
                        _movie.value = emptyArray
                    } else {
                        val random = (1 until movies.size).random()
                        _movie.value = re.replace(movies[random].title, "").toCharArray().map { item -> item.toString() }.toTypedArray()
                    }
                },
                onError = {
                    _error.value = it.message
                }
            ).addTo(compositeDisposable)

    }

    fun runTimer(){

        tickerFlow(expireDate - currentTime)
            .onEach { secondsRemaining ->
                _timer.value = secondsRemaining
            }
            .onCompletion {
                _endGame.value = true
            }
            .launchIn(viewModelScope)
    }

    private fun endGame(isFinished: Boolean){
        _endGame.value = isFinished
    }

    fun resetData(){
        _wordCounter.value = 0
        _timer.value = 0
    }
    fun saveScore(username: String, score: Int) =  viewModelScope.launch {
        withContext(Dispatchers.IO){
            hangmanRepository.updateScore(username, score)
        }
    }

    private fun tickerFlow(start: Long, end: Long = 0L) = flow {
        for (i in start downTo end) {
            emit(i)
            delay(1_000)
            if (endGame.value == true) {
                return@flow
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}