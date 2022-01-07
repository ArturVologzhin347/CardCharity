package com.example.cardcharity.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.LocalImageLoader
import com.example.cardcharity.utils.extensions.okHttpClient


private val LightThemeColors = lightColors(
    primary = light_primary,
    onPrimary = light_onPrimary,
    secondary = light_secondary,
    onSecondary = light_onSecondary,
    error = light_error,
    onError = light_onError,
    background = light_background,
    onBackground = light_onBackground,
    surface = light_surface,
    onSurface = light_onSurface
)

private val DarkThemeColors = darkColors(
    primary = dark_primary,
    onPrimary = dark_onPrimary,
    secondary = dark_secondary,
    onSecondary = dark_onSecondary,
    error = dark_error,
    onError = dark_onError,
    background = dark_background,
    onBackground = dark_onBackground,
    surface = dark_surface,
    onSurface = dark_onSurface
)










@Composable
fun CardCharityTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colors = if (darkTheme) {
        DarkThemeColors
    } else {
        LightThemeColors
    }

    val imageLoader = ImageLoader
        .Builder(context)
        .okHttpClient(context.okHttpClient)
        .build()

    CompositionLocalProvider(
        LocalImageLoader provides imageLoader
    ) {
        MaterialTheme(
            colors = colors,
            typography = AppTypography,
            shapes = Shapes,
            content = content
        )
    }
}

@Composable
fun PreviewTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) {
        DarkThemeColors
    } else {
        LightThemeColors
    }

    MaterialTheme(
        colors = colors,
        typography = AppTypography,
        shapes = Shapes,
        content = content
    )
}

