package com.example.ualchallenge.data.remote

import com.example.ualchallenge.data.local.CountryDao
import com.example.ualchallenge.data.local.CountryEntity
import com.example.ualchallenge.utils.DataError
import com.example.ualchallenge.utils.Result.Error
import com.example.ualchallenge.utils.Result.Success
import com.example.ualchallenge.utils.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class CountriesRepositoryImp(private val service: CountriesService, private val dao: CountryDao) :
    CountriesRepository {

    override suspend fun fetchCountries() {
        withContext(Dispatchers.IO) {
            when (val result = service.fetchApiData()) {
                is Error ->
                    when (result.error) {
                        DataError.NetWork.REQUEST_TIMEOUT -> println(result.error)
                    }

                is Success -> {
                    val remoteCountries = mutableListOf<CountryEntity>()
                    convertResponseToTempFileAndRead(result.data).collect {
                        remoteCountries.add(it.toEntity())
                        if (remoteCountries.size == 10_000) {
                            dao.insertAll(remoteCountries)
                            remoteCountries.clear()
                        }
                    }

                    if (remoteCountries.isNotEmpty())
                        dao.insertAll(remoteCountries)
                }
            }
        }
    }

    private fun convertResponseToTempFileAndRead(data: ByteArray): Flow<CountryData> =
        flow {
            val file = File.createTempFile("countries", null)
            val fileReader = FileReader(file.path)

            try {
                file.writeBytes(data)
                BufferedReader(fileReader).use { bufferedReader ->
                    var content: String? = ""

                    while (content != null) {
                        content = bufferedReader.readLine()
                        val trimmedText = content.trim().removeSuffix(",")
                        if (trimmedText.isNotEmpty()) {
                            try {
                                val country =
                                    Json.decodeFromString<CountryData>(trimmedText)
                                emit(country)
                            } catch (e: Exception) {
                                println(e.message)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                println(e.message)
            } finally {
                file.delete()
            }
        }.flowOn(Dispatchers.IO)
}