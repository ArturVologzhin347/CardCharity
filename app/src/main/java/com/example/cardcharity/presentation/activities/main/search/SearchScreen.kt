package com.example.cardcharity.presentation.activities.main.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cardcharity.presentation.theme.PreviewTheme

@Composable
fun SearchScreen(
    reduce: (event: SearchEvent) -> Unit,
    viewState: SearchViewState
) {
    Surface(
        color = MaterialTheme.colors.surface,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {



        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SearchScreenPreview() {
    PreviewTheme {
        SearchScreen(
            reduce = {},
            viewState = loadData()
        )
    }
}