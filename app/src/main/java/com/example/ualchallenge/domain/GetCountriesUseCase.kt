package com.example.ualchallenge.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.ualchallenge.data.local.CountryDao
import com.example.ualchallenge.data.remote.CountriesRepository
import com.example.ualchallenge.domain.model.CountryModel
import com.example.ualchallenge.utils.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class GetCountriesUseCase(
    private val dao: CountryDao,
    private val repository: CountriesRepository
) {
    operator fun invoke(query: String = ""): Flow<PagingData<CountryModel>> {
        runBlocking(Dispatchers.IO) {
            if (dao.isEmpty()) {
                repository.fetchCountries()
            }
        }
        return Pager(
            PagingConfig(
                pageSize = 10,
                prefetchDistance = 20,
            )
        )
        {
            when{
                query.isNotBlank() -> dao.getCountriesByName(query)
                else -> dao.getCountriesPaged()
            }
        }.flow
            .map { pagingData -> pagingData.map { countryEntity -> countryEntity.toModel() } }
    }
}