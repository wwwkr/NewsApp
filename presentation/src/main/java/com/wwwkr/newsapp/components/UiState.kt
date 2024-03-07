package com.wwwkr.newsapp.components

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val errorMessage: String) : UiState<Nothing>()

    object Empty : UiState<Nothing>()

    fun isLoading(): Boolean {
        return this is Loading
    }

    fun isSuccess(): Boolean {
        return this is Success
    }

    fun isError(): Boolean {
        return this is Error
    }
}