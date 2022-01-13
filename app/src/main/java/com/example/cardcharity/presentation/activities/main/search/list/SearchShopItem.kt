package com.example.cardcharity.presentation.activities.main.search.list

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.utils.extensions.firstReplacedSplit
import com.example.cardcharity.utils.extensions.trimmedIsEmpty
import timber.log.Timber

data class SearchShopItem(
    val shop: Shop,
    private val searched: String
) {

    fun getAnnotatedName(
        focusedStyle: SpanStyle,
        unfocusedStyle: SpanStyle
    ): AnnotatedString {
        return buildAnnotatedString {
            with(shop.name) {

                if (searched.trimmedIsEmpty()) {
                    withStyle(unfocusedStyle) { append(this@with) }
                    return@buildAnnotatedString
                }

                val parts = firstReplacedSplit(searched)
                val before = parts[0]
                val after = parts[1]
                val selected = substring(before.length, length - after.length)
                withStyle(unfocusedStyle) { append(before) }
                withStyle(focusedStyle) { append(selected) }
                withStyle(unfocusedStyle) { append(after) }
            }
        }
    }
}