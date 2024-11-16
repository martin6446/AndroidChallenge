package com.example.ualchallenge.countriesView

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ListView(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(10){
            ListItem(
                headlineContent = {
                    Text("My text")
                },
                supportingContent = { Text("asdf") },
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
    ListView()
}