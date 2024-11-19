package com.example.ualchallenge.domain

import com.example.ualchallenge.data.local.CountryDao
import com.example.ualchallenge.data.local.CountryEntity

class SetFavoriteCountryUseCase(
    private val dao: CountryDao,
) {
    suspend operator fun invoke(countryEntity: CountryEntity){
        dao.updateCountry(countryEntity)
    }
}