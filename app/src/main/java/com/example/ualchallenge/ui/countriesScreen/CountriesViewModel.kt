package com.example.ualchallenge.ui.countriesScreen

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ualchallenge.domain.GetCountriesUseCase
import com.example.ualchallenge.domain.GetFavoriteCountriesUseCase
import com.example.ualchallenge.domain.SetFavoriteCountryUseCase
import com.example.ualchallenge.domain.model.CountryModel
import com.example.ualchallenge.utils.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class CountriesViewModel(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val setFavoriteCountryUseCase: SetFavoriteCountryUseCase,
    private val getFavoriteCountriesUseCase: GetFavoriteCountriesUseCase
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    var countries: Flow<PagingData<CountryModel>> = getCountriesUseCase().cachedIn(viewModelScope)

    var favoriteCountries: Flow<PagingData<CountryModel>> = getFavoriteCountriesUseCase().cachedIn(viewModelScope)

    private val _showFavorites = MutableStateFlow(false)
    val showFavorites = _showFavorites.asStateFlow()

    fun showFavorites(show: Boolean){
        _showFavorites.update { show }
    }

    fun getCountriesByName(text: String) {
        _searchText.update { text }
        _isSearching.update { true }
        countries = getCountriesUseCase(text).cachedIn(viewModelScope)
        _isSearching.update { false }
    }

    fun clearSearch(){
        _searchText.update { "" }
        _isSearching.update { true }
        countries = getCountriesUseCase().cachedIn(viewModelScope)
        _isSearching.update { false }
    }

    fun setFavoriteCountry(countryModel: CountryModel) {
        viewModelScope.launch {
            setFavoriteCountryUseCase(
                countryModel.toEntity().copy(isFavorite = !countryModel.isFavorite)
            )
        }
    }

}


