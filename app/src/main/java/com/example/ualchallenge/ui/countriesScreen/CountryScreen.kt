package com.example.ualchallenge.ui.countriesScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel

@Composable
fun CountriesListView(modifier: Modifier = Modifier, viewmodel: CountriesViewModel = koinViewModel()) {

    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(viewmodel.countries){ country ->
            ListItem(
                headlineContent = {
                    Text(country.cityName)
                },
                supportingContent = { Text(country.countryName) },
                trailingContent = {
                    Icon(
                        Icons.Default.Favorite, contentDescription = null
                    )
                }
            )
            HorizontalDivider()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListViewPreview() {
    CountriesListView()
}