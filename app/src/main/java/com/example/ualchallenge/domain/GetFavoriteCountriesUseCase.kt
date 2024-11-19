package com.example.ualchallenge.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.ualchallenge.data.local.CountryDao
import com.example.ualchallenge.domain.model.CountryModel
import com.example.ualchallenge.utils.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFavoriteCountriesUseCase(
    private val dao: CountryDao
) {
    operator fun invoke(): Flow<PagingData<CountryModel>> {
        return Pager(
            PagingConfig(
                pageSize = 10,
                prefetchDistance = 20,
            )
        )
        {
            val data = dao.getFavoritesCountries()
            data
        }.flow
            .map { pagingData -> pagingData.map { countryEntity -> countryEntity.toModel() } }
    }
}