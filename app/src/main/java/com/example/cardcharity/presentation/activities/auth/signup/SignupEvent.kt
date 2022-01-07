package com.example.cardcharity.presentation.activities.auth.signup

sealed class SignupEvent {

    object Back : SignupEvent()

    data class Signup(
        val email: String,
        val password: String,
        val confirm: String
    ) : SignupEvent()


    companion object {

        fun back() = Back

        fun signup(
            email: String,
            password: String,
            confirm: String
        ) = Signup(email, password, confirm)

    }

}