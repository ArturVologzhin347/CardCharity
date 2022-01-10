package com.example.cardcharity.presentation.activities.password.reset

sealed class ResetPasswordEvent

object Finish : ResetPasswordEvent()

object ForgotPassword : ResetPasswordEvent()

data class ResetPassword(
    val oldPassword: String,
    val newPassword: String,
    val repeatPassword: String
) : ResetPasswordEvent()


fun finish() = Finish

fun forgotPassword() = ForgotPassword

fun resetPassword(
    oldPassword: String,
    newPassword: String,
    repeatPassword: String
) = ResetPassword(
    oldPassword = oldPassword,
    newPassword = newPassword,
    repeatPassword = repeatPassword
)
