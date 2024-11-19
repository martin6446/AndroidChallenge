package com.example.ualchallenge.domain.model

import com.example.ualchallenge.data.remote.Coord

data class CountryModel(
    val id: Int,
    val cityName: String,
    val countryName:String,
    val coordinates: Coord,
    val isFavorite: Boolean
)
