package com.example.ualchallenge.ui.googleMapsView

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState


@Composable
fun GoogleMapsScreen(
    modifier: Modifier = Modifier,
    lon: Double,
    lat: Double
) {
    val uiSettings by remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
    }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.TERRAIN))
    }
    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(lat,lon), 10f
        )
    }
    val marker = rememberMarkerState(position = LatLng(lat,lon))

    LaunchedEffect(lat,lon) {
        cameraPosition.move(CameraUpdateFactory.newLatLngZoom(LatLng(lat,lon),10f))
        marker.position = LatLng(lat,lon)
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPosition,
        properties = properties,
        uiSettings = uiSettings
    ) {
        Marker(
            state = marker
        )
    }
}