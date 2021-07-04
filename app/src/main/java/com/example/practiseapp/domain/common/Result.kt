package com.example.practiseapp.domain.common


sealed class Result<out T> {
    class Success<out T>(val data: T) : Result<T>()
    class Failure(val exception: Exception) : Result<Nothing>()

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