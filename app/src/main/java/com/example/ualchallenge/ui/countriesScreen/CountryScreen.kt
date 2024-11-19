package com.example.ualchallenge.ui.countriesScreen

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.ualchallenge.data.remote.Coord
import com.example.ualchallenge.domain.model.CountryModel
import com.example.ualchallenge.ui.googleMapsView.GoogleMapsScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun CountriesScreen(
    modifier: Modifier = Modifier,
    viewmodel: CountriesViewModel = koinViewModel(),
    navigateToMap: (Double, Double) -> Unit
) {
    val pagingCountries = viewmodel.countries.collectAsLazyPagingItems()
    val favoritePagingCountries = viewmodel.favoriteCountries.collectAsLazyPagingItems()
    val searchText by viewmodel.searchText.collectAsState()
    val isSearching by viewmodel.isSearching.collectAsState()
    val showFavorites by viewmodel.showFavorites.collectAsState()

    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE


    if (isLandscape) {
        LandScapeOrientation(
            modifier,
            searchText,
            viewmodel,
            showFavorites,
            isSearching,
            favoritePagingCountries,
            pagingCountries
        )
    } else {
        CountryList(
            modifier,
            searchText,
            viewmodel,
            showFavorites,
            isSearching,
            favoritePagingCountries,
            pagingCountries,
            navigateToMap
        )
    }
}

@Composable
fun LandScapeOrientation(
    modifier: Modifier,
    searchText: String,
    viewmodel: CountriesViewModel,
    showFavorites: Boolean,
    isSearching: Boolean,
    favoritePagingCountries: LazyPagingItems<CountryModel>,
    pagingCountries: LazyPagingItems<CountryModel>,
) {
    var latitude by remember { mutableDoubleStateOf(0.0) }
    var longitude by remember { mutableDoubleStateOf(0.0) }

    Row(modifier = modifier.fillMaxSize()) {
        CountryList(
            modifier.weight(1f),
            searchText,
            viewmodel,
            showFavorites,
            isSearching,
            favoritePagingCountries,
            pagingCountries,
            onItemClicked = { lat, lon ->
                latitude = lat
                longitude = lon
            }
        )

        GoogleMapsScreen(
            modifier = Modifier.weight(2f),
            lat = latitude,
            lon = longitude
        )
    }
}

@Composable
private fun CountryList(
    modifier: Modifier,
    searchText: String,
    viewmodel: CountriesViewModel,
    showFavorites: Boolean,
    isSearching: Boolean,
    favoritePagingCountries: LazyPagingItems<CountryModel>,
    pagingCountries: LazyPagingItems<CountryModel>,
    onItemClicked: (Double, Double) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        Row {
            TextField(
                modifier = Modifier.weight(1f),
                value = searchText,
                onValueChange = viewmodel::getCountriesByName,
                placeholder = { Text("Country") },
                trailingIcon = {
                    Icon(
                        Icons.Default.Clear, "clear search",
                        modifier = Modifier.clickable {
                            viewmodel.clearSearch()
                        }
                    )
                }
            )

            Button(
                onClick = { viewmodel.showFavorites(!showFavorites) }
            ) {
                Text("Toggle Favorites")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (isSearching) {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                val data = if (showFavorites) favoritePagingCountries else pagingCountries

                items(
                    count = data.itemCount,
                    key = data.itemKey { it.id },
                    contentType = data.itemContentType()
                ) { index ->
                    val country = data[index]
                    country?.let {
                        CountryItem(
                            it,
                            onFavIcon = viewmodel::setFavoriteCountry,
                            onClick = {
                                onItemClicked(it.coordinates.lat, it.coordinates.lon)
                            }
                        )
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
private fun CountryItem(
    country: CountryModel,
    onFavIcon: (CountryModel) -> Unit = {},
    onClick: () -> Unit = {}
) {

    val color by animateColorAsState(
        if (country.isFavorite) {
            Color.Red
        } else {
            LocalContentColor.current
        }, label = "favorite color"
    )
    ListItem(
        headlineContent = {
            Text("${country.cityName} (${country.countryName})")
        },
        Modifier.clickable {
            onClick()
        },
        supportingContent = { Text("LAT: ${country.coordinates.lat}, LON: ${country.coordinates.lon}") },
        trailingContent = {
            Icon(
                Icons.Default.Favorite,
                modifier = Modifier.clickable {
                    onFavIcon(country)
                },
                contentDescription = null,
                tint = color
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
private fun ListViewPreview() {
    CountryItem(
        CountryModel(
            123,
            "alabama",
            "asdfasd",
            Coord(123.0, 123.0),
            true
        )
    )
}