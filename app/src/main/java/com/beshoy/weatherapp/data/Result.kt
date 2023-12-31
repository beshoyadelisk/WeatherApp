package com.beshoy.weatherapp.data

enum class ErrorType {
    CLIENT,
    SERVER,
    GENERIC,
    IO_CONNECTION,
    UNAUTHORIZED
}

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error<T>(val errorType: ErrorType) : Result<T>
    data object Loading : Result<Nothing>
}
