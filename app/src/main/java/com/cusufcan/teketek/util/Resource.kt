package com.cusufcan.teketek.util

sealed class Resource<T> {
    class Loading<T>(val data: T) : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String, val data: T) : Resource<T>()
}