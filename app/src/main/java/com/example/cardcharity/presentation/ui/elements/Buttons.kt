package com.example.cardcharity.presentation.ui.elements

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun AppButton(
    onClick: () -> Unit,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        content = content
    )
}