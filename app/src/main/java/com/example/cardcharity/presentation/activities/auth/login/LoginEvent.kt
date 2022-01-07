package com.example.cardcharity.presentation.activities.auth.login

sealed class LoginEvent {

    object Go : LoginEvent()

    data class LoginWithEmailAndPassword(
        val email: String,
        val password: String
    ) : LoginEvent()

    object LoginWithGoogle : LoginEvent()

    data class ForgotPassword(val email: String) : LoginEvent()

    object Signup : LoginEvent()

    companion object {

        fun go() = Go

        fun loginWithEmailAndPassword(
            email: String,
            password: String
        ) = LoginWithEmailAndPassword(email, password)

        fun loginWithGoogle() = LoginWithGoogle

        fun forgotPassword(email: String) = ForgotPassword(email)

        fun signup() = Signup

    }

}
