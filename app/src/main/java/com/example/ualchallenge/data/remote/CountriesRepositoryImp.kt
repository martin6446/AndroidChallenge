package com.example.ualchallenge.data.remote

import com.example.ualchallenge.data.local.CountryDao
import com.example.ualchallenge.data.local.CountryEntity
import com.example.ualchallenge.model.CountryModel
import com.example.ualchallenge.utils.Result.Error
import com.example.ualchallenge.utils.Result.Success
import com.example.ualchallenge.utils.toEntity
import com.example.ualchallenge.utils.toModel
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

    override suspend fun getCountries(): List<CountryModel> {
        val localCountries = dao.getCountries()

        if (localCountries.isNotEmpty()) {
            return localCountries.map { it.toModel() }
        }

        return withContext(Dispatchers.IO) {
            when (val result = service.fetchApiData()) {
                is Error -> TODO()
                is Success -> {
                    val remoteCountries = mutableListOf<CountryEntity>()
                    convertResponseToTempFileAndRead(result.data).collect {
                        remoteCountries.add(it.toEntity())
                        if (remoteCountries.size == 10_000) {
                            dao.insertAll(remoteCountries)
                            remoteCountries.clear()
                        }
                    }

                    if(remoteCountries.isNotEmpty())
                        dao.insertAll(remoteCountries)

                    dao.getCountries().map { it.toModel() }
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