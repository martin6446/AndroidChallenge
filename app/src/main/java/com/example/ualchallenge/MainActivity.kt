package com.example.ualchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.ualchallenge.ui.NavigationWrapper
import com.example.ualchallenge.ui.countriesScreen.CountriesScreen
import com.example.ualchallenge.ui.theme.UaláChallengeTheme
import org.koin.androidx.compose.KoinAndroidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UaláChallengeTheme {
                KoinAndroidContext {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) { innerPadding ->
                        NavigationWrapper(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}