package com.example.ualchallenge.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class CountryEntity(
    @PrimaryKey
    val id: Int,
    val cityName: String,
    val countryName: String,
    val isFavorite: Boolean = false,
    val lat: Double,
    val lon: Double
)