package com.example.cardcharity.utils.extensions

import android.util.Patterns

fun String?.trimmedIsEmpty(): Boolean {
    if(this == null) {
        return true
    }

    return this.trim().isEmpty()
}


fun String.firstInLowercase(): String {
    return first().toString().lowercase()
}

//"Hello World".cut(4) -> "Hell"
fun String.cut(n: Int): String {
    if (length < n) return this
    return this.dropLast(length - n)
}

fun String.toAbbreviation(maxLength: Int): String {
    return this.replace("\\B.|\\P{L}".toRegex(), "").uppercase().cut(maxLength)
}

fun CharSequence.isNotEmail(): Boolean {
    return !isEmail()
}

fun CharSequence.isEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}