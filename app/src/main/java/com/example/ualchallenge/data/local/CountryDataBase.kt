package com.example.ualchallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CountryEntity::class],
    version = 1
)
abstract class CountryDataBase: RoomDatabase() {
    abstract fun getDao(): CountryDao
}