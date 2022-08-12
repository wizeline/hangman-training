package com.wizeline.academy.hangman.ui.score

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wizeline.academy.hangman.data.model.Score
import com.wizeline.academy.hangman.data.repository.HangmanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    private val hangmanRepository: HangmanRepository
) : ViewModel() {


    private val _scoreList = MutableLiveData<List<Score>>()
    val scoreList : MutableLiveData<List<Score>>
        get() = _scoreList


    fun getScoreList() =  viewModelScope.launch {
        _scoreList.value = withContext(Dispatchers.IO){ hangmanRepository.getScores() }
    }

}