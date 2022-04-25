package com.example.jpmc_coding_challenge.data.model

sealed class UIState<out T>{
    object Loading: UIState<Nothing>()
    data class Error(val msg: String): UIState<Nothing>()
    data class Success<T>(val response: T): UIState<T>()
}
