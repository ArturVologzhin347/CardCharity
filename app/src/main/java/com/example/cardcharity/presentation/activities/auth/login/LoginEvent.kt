package com.example.cardcharity.presentation.activities.auth.login

import com.example.cardcharity.presentation.base.mvi.MviEvent

sealed class LoginEvent: MviEvent

object Go : LoginEvent()

data class LoginWithEmailAndPassword(
    val email: String,
    val password: String
) : LoginEvent()

object LoginWithGoogle : LoginEvent()

data class ForgotPassword(val email: String) : LoginEvent()

object Signup : LoginEvent()


fun go() = Go

fun loginWithEmailAndPassword(
    email: String,
    password: String
) = LoginWithEmailAndPassword(email, password)

fun loginWithGoogle() = LoginWithGoogle

fun forgotPassword(email: String) = ForgotPassword(email)

fun signup() = Signup



