package com.example.ualchallenge.data.remote

import com.example.ualchallenge.model.CountryModel
import com.example.ualchallenge.utils.Result
import com.example.ualchallenge.utils.RootError
import kotlinx.coroutines.flow.Flow

interface CountriesRepository {
    suspend fun getCountries(): List<CountryModel>
}