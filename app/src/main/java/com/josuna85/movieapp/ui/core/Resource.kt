package com.josuna85.movieapp.ui.core

import java.lang.Exception

sealed class Resource<out T> {
    class Loading<out T>: Resource<T>()
    data class Success<out T>(val data:T): Resource<T>()
    data class Failure(val exception: Exception): Resource<Nothing>()

}