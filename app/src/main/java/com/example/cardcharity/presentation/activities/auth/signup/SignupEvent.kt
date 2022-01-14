package com.example.cardcharity.presentation.activities.auth.signup

import com.example.cardcharity.presentation.base.mvi.MviEvent

sealed class SignupEvent : MviEvent

object Back : SignupEvent()

object Go : SignupEvent()

data class Signup(
    val email: String,
    val password: String,
    val confirm: String
) : SignupEvent()


fun back() = Back

fun go() = Go

fun signup(
    email: String,
    password: String,
    confirm: String
) = Signup(email, password, confirm)
