package com.example.ualchallenge.ui.countriesScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ualchallenge.data.remote.CountriesRepository
import com.example.ualchallenge.model.CountryModel
import kotlinx.coroutines.launch

class CountriesViewModel(private val repository: CountriesRepository) : ViewModel() {

    var countries by mutableStateOf<List<CountryModel>>(listOf())
        private set

    init {
        getCountries()
    }

    private fun getCountries() {
        viewModelScope.launch {
            countries = repository.getCountries().sortedBy { it.cityName }
//            when(val result = repository.getCountries()){
//                is Result.Error -> {
//                    when(result.error){
//                        DataError.NetWork.REQUEST_TIMEOUT -> TODO()
//                    }
//                }
//                is Result.Success -> countries = result.data.sortedBy { it.cityName }
//            }
        }
    }


}