package com.tristaam.aovherorate.domain.model

sealed interface Result<out T> {
    data object Loading : Result<Nothing>
    data class Success<T>(val data: T) : Result<T>
    data class Error<T>(val exception: Throwable) : Result<T>
}