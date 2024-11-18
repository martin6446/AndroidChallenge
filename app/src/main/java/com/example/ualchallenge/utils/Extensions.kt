package com.example.ualchallenge.utils

import com.example.ualchallenge.data.local.CountryEntity
import com.example.ualchallenge.data.remote.Coord
import com.example.ualchallenge.data.remote.CountryData
import com.example.ualchallenge.model.CountryModel

fun CountryData.toModel() =
    CountryModel(
        cityName = name,
        countryName = country,
        coordinates = coord
    )

fun CountryData.toEntity() =
    CountryEntity(
        id = id,
        cityName = name,
        countryName = country,
        lat = coord.lat,
        lon = coord.lon
    )

fun CountryEntity.toModel() =
    CountryModel(
        cityName = cityName,
        countryName = countryName,
        coordinates = Coord(lat, lon)
    )
