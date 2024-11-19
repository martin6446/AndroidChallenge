package com.example.ualchallenge.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.ualchallenge.ui.countriesScreen.CountriesScreen
import com.example.ualchallenge.ui.googleMapsView.GoogleMapsScreen


@Composable
fun NavigationWrapper(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = CountriesView
    ) {
        composable<CountriesView> {
            CountriesScreen() { lat, lon ->
                navController.navigate(GoogleMapsView(lat, lon))
            }
        }
        composable<GoogleMapsView> {
            val detail = it.toRoute<GoogleMapsView>()
            GoogleMapsScreen(lat = detail.lat, lon = detail.lon)
        }
    }
}