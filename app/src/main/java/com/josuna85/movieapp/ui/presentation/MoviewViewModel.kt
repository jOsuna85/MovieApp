package com.josuna85.movieapp.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.josuna85.movieapp.ui.core.Resource
import com.josuna85.movieapp.ui.repository.MovieRepository
import kotlinx.coroutines.Dispatchers


class MoviewViewModel(private val repo:MovieRepository): ViewModel() {

    fun fetchMainScreenMovies() = liveData(viewModelScope.coroutineContext + Dispatchers.Main){
        emit(Resource.Loading())

        try{
            emit(Resource.Success(Triple(repo.getPopularMovies(),repo.getUpcomingMovies(),repo.getTopRatedMovies())))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }


}

class MovieViewModelFactory(private val repo: MovieRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repo)
    }

}