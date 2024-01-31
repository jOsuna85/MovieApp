package com.josuna85.movieapp.ui.data.remote

import com.josuna85.movieapp.ui.application.AppConstants
import com.josuna85.movieapp.ui.data.model.MovieList
import com.josuna85.movieapp.ui.repository.WebService

class RemoteMovieDataSource(private val webService: WebService) {

    suspend fun getUpcomingMovies(): MovieList = webService.getUpcomingMovies(AppConstants.API_KEY)

    suspend fun getTopRatedMovies(): MovieList = webService.getTopRatedMovies(AppConstants.API_KEY)

    suspend fun getPopularMovies(): MovieList = webService.getPopularMovies(AppConstants.API_KEY)
}