package com.example.ualchallenge.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CountryDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<CountryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(countries: CountryEntity)

    @Update
    suspend fun updateCountry(countryEntity: CountryEntity)

    @Query("SELECT (SELECT COUNT(*) FROM CountryEntity) == 0")
    suspend fun isEmpty(): Boolean

    @Query("select * from CountryEntity where cityName like :name || '%' order by cityName asc ")
    fun getCountriesByName(name:String):PagingSource<Int,CountryEntity>

    @Query("select * from CountryEntity order by cityName asc")
    fun getCountriesPaged(): PagingSource<Int,CountryEntity>

    @Query("select * from CountryEntity where isFavorite == 1 order by cityName asc")
    fun getFavoritesCountries(): PagingSource<Int,CountryEntity>
}
