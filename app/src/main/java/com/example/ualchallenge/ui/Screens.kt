package com.example.ualchallenge.ui

import kotlinx.serialization.Serializable

@Serializable
object CountriesView

@Serializable
data class GoogleMapsView(val lat: Double, val lon: Double)