package com.example.cardcharity.utils

private val allowedChars = ('А'..'Я') + ('а'..'я')// + (0..9)
fun getRandomString(length: Int): String {
    return (1..length).map { allowedChars.random() }.joinToString("")
}