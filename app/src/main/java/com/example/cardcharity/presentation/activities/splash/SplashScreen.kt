package com.example.cardcharity.presentation.activities.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cardcharity.R
import com.example.cardcharity.presentation.theme.CardCharityTheme
import com.example.cardcharity.presentation.theme.PreviewTheme

@Composable
fun SplashScreen() {
    Surface(
        color = MaterialTheme.colors.surface,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Icon(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                tint = MaterialTheme.colors.primary,
                contentDescription = null,
                modifier = Modifier
                    .size(256.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    PreviewTheme {
        SplashScreen()
    }
}