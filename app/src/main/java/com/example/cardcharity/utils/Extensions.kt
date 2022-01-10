package com.example.cardcharity.utils

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

inline fun <reified T : Enum<T>> enumContains(name: String): Boolean {
    return enumValues<T>().any { it.name == name }
}

inline fun <reified T : Enum<T>> enumValueOfOrNull(name: String): T? {
    return enumValues<T>().find { it.name == name }
}

private val allowedChars = ('А'..'Я') + ('а'..'я')// + (0..9)
fun getRandomString(length: Int): String {
    return (1..length).map { allowedChars.random() }.joinToString("")
}

@OptIn(ExperimentalContracts::class)
fun checkOrNull(value: Boolean): Unit? {
    contract {
        returns() implies value
    }
    return if (value) Unit else null
}


inline fun Any?.ifNull(block: () -> Unit) {
    if(this == null) {
        block()
    }
}


