package com.example.ualchallenge.utils


sealed interface RootError

sealed interface DataError: RootError{
    enum class NetWork: DataError{
        REQUEST_TIMEOUT
    }
}

sealed interface Result<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : Result<D, E>
    data class Error<out D, out E : RootError>(val error: E) : Result<D, E>
}