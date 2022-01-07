package com.example.cardcharity.presentation.activities.welcome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cardcharity.presentation.theme.CardCharityTheme
import com.example.cardcharity.presentation.theme.onPrimaryContainer
import com.example.cardcharity.presentation.theme.primaryContainer

@Composable
fun WelcomeScreen() {
    Surface(
        color = MaterialTheme.colors.primaryContainer,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Text(
                text = "WELCOME",
                color = MaterialTheme.colors.onPrimaryContainer,
                modifier = Modifier.align(Alignment.Center)
            )

        }
    }
}


@Preview
@Composable
fun WelcomeScreenPreview() {
    CardCharityTheme {
        WelcomeScreen()
    }
}