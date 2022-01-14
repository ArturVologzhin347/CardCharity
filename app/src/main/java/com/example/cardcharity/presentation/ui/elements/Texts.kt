package com.example.cardcharity.presentation.ui.elements

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ButtonText(
    text: String,
    color: Color
) {
    Text(
        text = text,
        style = MaterialTheme.typography.button,
        fontWeight = FontWeight.Medium,
        color = color,
        fontSize = 16.sp
    )
}