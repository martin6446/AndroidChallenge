package com.example.ualchallenge.data.remote

import com.example.ualchallenge.utils.DataError
import com.example.ualchallenge.utils.Result
import com.example.ualchallenge.utils.Result.Error
import com.example.ualchallenge.utils.Result.Success
import com.example.ualchallenge.utils.RootError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.get
import io.ktor.client.request.url

class CountriesService(private val client: HttpClient) {

    private val countriesUrl =
        "https://gist.githubusercontent.com/hernan-uala/dce8843a8edbe0b0018b32e137bc2b3a/raw/0996accf70cb0ca0e16f9a99e0ee185fafca7af1/cities.json"

    suspend fun fetchApiData(): Result<ByteArray, RootError> {

        try {
            val data: ByteArray = client.get {
                url(countriesUrl)

            }.body()

            return Success(data)
        } catch (e: HttpRequestTimeoutException) {
            return Error(DataError.NetWork.REQUEST_TIMEOUT)
        }
    }
}