package com.example.ualchallenge.utils

import com.example.ualchallenge.data.local.CountryEntity
import com.example.ualchallenge.data.remote.Coord
import com.example.ualchallenge.data.remote.CountryData
import com.example.ualchallenge.domain.model.CountryModel


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
        id = id,
        cityName = cityName,
        countryName = countryName,
        coordinates = Coord(lat, lon),
        isFavorite = isFavorite
    )

fun CountryModel.toEntity() =
    CountryEntity(
        id = id,
        cityName = cityName,
        countryName = countryName,
        lat = coordinates.lat,
        lon = coordinates.lon
    )
