package com.example.ualchallenge.data.remote

interface CountriesRepository {
    suspend fun fetchCountries()
}