package com.wizeline.academy.hangman.data.services

import com.wizeline.academy.hangman.data.model.MoviesRespose
import retrofit2.http.GET
import retrofit2.http.Query
import com.wizeline.academy.hangman.util.API_KEY
import io.reactivex.Single



interface MoviesApi {
    @GET("popular")
    fun getMovie(
        @Query("api_key") api_key: String = API_KEY,
        @Query("page") page: Int
    ): Single<MoviesRespose>
}