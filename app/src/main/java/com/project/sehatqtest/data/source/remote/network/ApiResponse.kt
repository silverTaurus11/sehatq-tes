package com.project.sehatqtest.data.source.remote.network

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val errorException: Throwable) : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
}