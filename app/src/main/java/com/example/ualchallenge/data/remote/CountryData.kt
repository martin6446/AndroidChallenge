package com.example.ualchallenge.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryData(
    @SerialName("_id")
    val id: Int,
    val coord: Coord,
    val country: String,
    val name: String
)

