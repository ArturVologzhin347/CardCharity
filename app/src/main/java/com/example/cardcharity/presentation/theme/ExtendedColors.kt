package com.example.cardcharity.presentation.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


//"Material You" colors without material you =)


val Colors.surface8Primary: Color
    @Composable get() = primary.copy(alpha = 0.08F)

val Colors.primaryContainer: Color
    @Composable get() = if (isLight) light_primaryContainer else dark_primaryContainer

val Colors.onPrimaryContainer: Color
    @Composable get() = if (isLight) light_onPrimaryContainer else dark_onPrimaryContainer

val Colors.secondaryContainer: Color
    @Composable get() = if (isLight) light_secondaryContainer else dark_secondaryContainer

val Colors.onSecondaryContainer: Color
    @Composable get() = if (isLight) light_onSecondaryContainer else dark_onSecondaryContainer

val Colors.tertiary: Color
    @Composable get() = if (isLight) light_tertiary else dark_tertiary

val Colors.onTertiary: Color
    @Composable get() = if (isLight) light_onTertiary else dark_onTertiary

val Colors.tertiaryContainer: Color
    @Composable get() = if (isLight) light_tertiaryContainer else dark_tertiaryContainer

val Colors.onTertiaryContainer: Color
    @Composable get() = if (isLight) light_onTertiaryContainer else dark_onTertiaryContainer

val Colors.errorContainer: Color
    @Composable get() = if (isLight) light_errorContainer else dark_errorContainer

val Colors.onErrorContainer: Color
    @Composable get() = if (isLight) light_onErrorContainer else dark_onErrorContainer

val Colors.surfaceVariant: Color
    @Composable get() = if (isLight) light_surfaceVariant else dark_surfaceVariant

val Colors.onSurfaceVariant: Color
    @Composable get() = if (isLight) light_onSurfaceVariant else dark_onSurfaceVariant

val Colors.outline: Color
    @Composable get() = if (isLight) light_outline else dark_outline

val Colors.inverseSurface: Color
    @Composable get() = if (isLight) light_inverseSurface else dark_inverseSurface

val Colors.inverseOnSurface: Color
    @Composable get() = if (isLight) light_inverseOnSurface else dark_inverseOnSurface

val Colors.inversePrimary: Color
    @Composable get() = if (isLight) light_inversePrimary else dark_inversePrimary

val Colors.shadow: Color
    @Composable get() = if (isLight) light_shadow else dark_shadow