package com.example.cardcharity.presentation.ui.elements

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cardcharity.R

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colors.onBackground
) {
    TopButton(
        onClick = onClick,
        painter = painterResource(id = R.drawable.ic_arrow_back_24),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun TopButton(
    onClick: () -> Unit,
    painter: Painter,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colors.onBackground
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = tint,
            modifier = modifier
                .padding(all = 16.dp)
                .size(24.dp)
        )
    }
}
