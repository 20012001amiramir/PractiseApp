package com.example.practiseapp.domain.common


sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val exception: Exception) : Result<Nothing>()

    fun onResult(
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        when (this) {
            is Success -> onSuccess
            is Failure -> onFailure
        }
    }
}