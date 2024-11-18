package com.example.ualchallenge.model

import com.example.ualchallenge.data.remote.Coord

data class CountryModel(
    val cityName: String,
    val countryName:String,
    val coordinates: Coord
)
