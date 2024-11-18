package com.example.ualchallenge.data.local

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

    @Query("select * from CountryEntity where cityName like :name")
    suspend fun getCountry(name:String): CountryEntity

    @Query("select * from CountryEntity")
    suspend fun getCountries(): List<CountryEntity>

    @Query("select * from CountryEntity where isFavorite = 1")
    suspend fun getFavoritesCountries(): List<CountryEntity>
}
