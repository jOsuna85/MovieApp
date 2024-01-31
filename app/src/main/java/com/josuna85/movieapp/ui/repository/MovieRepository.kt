package com.josuna85.movieapp.ui.repository

import com.josuna85.movieapp.ui.data.model.MovieList

interface MovieRepository {
    suspend fun getUpcomingMovies(): MovieList
    suspend fun getTopRatedMovies(): MovieList
    suspend fun getPopularMovies(): MovieList

}