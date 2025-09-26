package com.example.smartcitizenclub.core.utils

/**
 * A generic class that holds a value or an error.
 * Used for handling API responses and business logic results.
 */
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

/**
 * Extension function to check if result is successful
 */
fun <T> Result<T>.isSuccess(): Boolean = this is Result.Success

/**
 * Extension function to check if result is error
 */
fun <T> Result<T>.isError(): Boolean = this is Result.Error

/**
 * Extension function to check if result is loading
 */
fun <T> Result<T>.isLoading(): Boolean = this is Result.Loading

/**
 * Extension function to get data from success result
 */
fun <T> Result<T>.getDataOrNull(): T? = when (this) {
    is Result.Success -> data
    else -> null
}

/**
 * Extension function to get error from error result
 */
fun <T> Result<T>.getErrorOrNull(): Throwable? = when (this) {
    is Result.Error -> exception
    else -> null
}
