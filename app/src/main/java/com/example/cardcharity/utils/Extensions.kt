package com.example.cardcharity.utils


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


