package com.example.cardcharity.utils.extensions

import android.util.Patterns
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle

fun String?.trimmedIsEmpty(): Boolean {
    if (this == null) {
        return true
    }

    return this.trim().isEmpty()
}

fun String?.trimmedIsNotEmpty(): Boolean {
    return !trimmedIsEmpty()
}

fun String?.nullIfEmpty(): String? {
    return if (this?.isEmpty() != false) null else this
}

fun String?.nullIfTrimmedEmpty(): String? {
    return if (this?.trim()?.isEmpty() != false) null else this
}

fun String.firstInLowercase(): String {
    return first().toString().lowercase()
}

fun String.firstInUppercase(): String {
    return first().toString().uppercase()
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

