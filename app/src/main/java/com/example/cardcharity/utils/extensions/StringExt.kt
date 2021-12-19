package com.example.cardcharity.utils.extensions

import java.util.*


fun String.firstInLowercase(): String {
    return first().toString().lowercase()
}

fun String.capitalize(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    }
}