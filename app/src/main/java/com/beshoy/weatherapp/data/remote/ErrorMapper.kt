package com.beshoy.weatherapp.data.remote

import androidx.annotation.StringRes
import com.beshoy.weatherapp.R
import com.beshoy.weatherapp.data.error.ClientException
import com.beshoy.weatherapp.data.ErrorType
import com.beshoy.weatherapp.data.error.GenericException
import com.beshoy.weatherapp.data.error.ServerException
import com.beshoy.weatherapp.data.error.UnauthorizedException
import java.io.IOException
import java.net.HttpURLConnection

fun mapResponseCodeToThrowable(code: Int): Throwable = when (code) {
    HttpURLConnection.HTTP_UNAUTHORIZED -> UnauthorizedException("Unauthorized access : $code")
    in 400..499 -> ClientException("Client error : $code")
    in 500..600 -> ServerException("Server error : $code")
    else -> GenericException("Generic error : $code")
}

fun mapThrowableToErrorType(throwable: Throwable): ErrorType {
    val errorType = when (throwable) {
        is IOException -> ErrorType.IO_CONNECTION
        is ClientException -> ErrorType.CLIENT
        is ServerException -> ErrorType.SERVER
        is UnauthorizedException -> ErrorType.UNAUTHORIZED
        else -> ErrorType.GENERIC
    }
    return errorType
}
@StringRes
fun ErrorType.toResourceId(): Int = when (this) {
    ErrorType.SERVER -> R.string.error_server
    ErrorType.GENERIC -> R.string.error_generic
    ErrorType.IO_CONNECTION -> R.string.error_connection
    ErrorType.UNAUTHORIZED -> R.string.error_unauthorized
    ErrorType.CLIENT -> R.string.error_client
}

