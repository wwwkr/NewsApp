package com.wwwkr.domain.extensions

import com.wwwkr.domain.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow

fun<T> MutableStateFlow<UiState<List<T>>>.toDataList(): List<T> {
    return if (value is UiState.Success<List<T>>) {
        (value as UiState.Success<List<T>>).data
    } else listOf()
}

fun <T> UiState<List<T>>.toDataList(): List<T> {
    return when (this) {
        is UiState.Success -> this.data
        else -> emptyList()
    }
}