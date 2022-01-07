package com.example.cardcharity.presentation.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun NestedResizeColumn(context: @Composable ColumnScope.() -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                content = context
            )
        }
    }
}