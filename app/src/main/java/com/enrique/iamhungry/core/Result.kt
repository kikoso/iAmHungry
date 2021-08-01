package com.enrique.iamhungry.core

sealed class Result<out R> {
    data class Success<out T>(val response: T) : Result<T>()
    data class Error(val failure: Failure) : Result<Nothing>()
}