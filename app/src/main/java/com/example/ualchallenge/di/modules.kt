package com.example.ualchallenge.di

import androidx.room.Room
import com.example.ualchallenge.data.local.CountryDataBase
import com.example.ualchallenge.data.remote.CountriesRepository
import com.example.ualchallenge.data.remote.CountriesRepositoryImp
import com.example.ualchallenge.data.remote.CountriesService
import com.example.ualchallenge.ui.countriesScreen.CountriesViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::provideHttpClient)
    singleOf(::CountriesService)
    singleOf(::CountriesRepositoryImp) bind CountriesRepository::class
    viewModelOf(::CountriesViewModel)
}


val provideRoomDataBaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), CountryDataBase::class.java, "Countries").build()
    }
}

val daoModule = module {
    includes(provideRoomDataBaseModule)
    single {
        get<CountryDataBase>().getDao()
    }
}

fun provideHttpClient() =
    HttpClient(Android) {
        engine {
            connectTimeout = 10000
        }
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                },
                contentType = ContentType.Any
            )
        }
        install(Logging) {
            level = LogLevel.ALL
        }
    }