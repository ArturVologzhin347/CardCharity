package com.example.cardcharity.presentation.ui.elements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun NestedResizeColumn(
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    context: @Composable ColumnScope.() -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Column(
                verticalArrangement = verticalArrangement,
                horizontalAlignment = horizontalAlignment,
                modifier = Modifier.fillMaxWidth(),
                content = context
            )
        }
    }
}
