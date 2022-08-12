package com.wizeline.academy.hangman.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wizeline.academy.hangman.data.model.Movie
import com.wizeline.academy.hangman.data.repository.HangmanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(
    private val hangmanRepository: HangmanRepository
) : ViewModel() {

    private val _validUserName = MutableLiveData<Boolean>()
    val validUserName : MutableLiveData<Boolean>
        get() = _validUserName


    fun validateUserName(userName: String) = viewModelScope.launch {
        _validUserName.value =  withContext(Dispatchers.IO){
            hangmanRepository.getUserName(userName)?.userName?.isEmpty() ?: true
        }
    }

    fun saveUserName(userName: String) =  viewModelScope.launch {
        withContext(Dispatchers.IO){
            hangmanRepository.saveUserName(userName)
        }
    }


}