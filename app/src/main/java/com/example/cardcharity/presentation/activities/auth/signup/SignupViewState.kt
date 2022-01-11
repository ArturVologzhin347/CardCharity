package com.example.cardcharity.presentation.activities.auth.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.cardcharity.R
import com.example.cardcharity.presentation.base.mvi.MviViewState
import com.example.cardcharity.presentation.ui.UiException

sealed class SignupViewState: MviViewState

object Default : SignupViewState()

object Success : SignupViewState()

object Load : SignupViewState()

sealed class Fail(val locale: Locale, val exception: UiException) : SignupViewState()

object InvalidEmail : Fail(Locale.EMAIL, EmailInvalidException)
object InvalidPassword : Fail(Locale.PASSWORD, PasswordInvalidException)
object PasswordMismatch : Fail(Locale.CONFIRM, PasswordMismatchException)
object UserAlreadyExists : Fail(Locale.EMAIL, UserAlreadyExistException)
object NoNetworkConnection : Fail(Locale.COMMON, NoNetworkConnectionException)
object Unknown : Fail(Locale.COMMON, UnknownException)

private object EmailInvalidException : UiException(R.string.error_email_invalid)
private object PasswordInvalidException : UiException(R.string.password_help)
private object PasswordMismatchException : UiException(R.string.error_password_mismatch)
private object UserAlreadyExistException : UiException(R.string.error_user_already_exists)
private object NoNetworkConnectionException : UiException(R.string.error_no_network)
private object UnknownException : UiException(R.string.error_unknown)

enum class Locale {
    COMMON,
    EMAIL,
    PASSWORD,
    CONFIRM
}


fun default() = Default

fun success() = Success

fun load() = Load

fun failInvalidEmail() = InvalidEmail

fun failInvalidPassword() = InvalidPassword

fun failPasswordMismatch() = PasswordMismatch

fun failUserAlreadyExists() = UserAlreadyExists

fun failNoNetworkConnection() = NoNetworkConnection

fun failUnknown() = Unknown


data class SignupFailHandled(val fail: Fail) : Exception()

fun Fail.handled(): SignupFailHandled {
    return SignupFailHandled(this)
}

fun SignupViewState.failOrNot(locale: Locale): Boolean {
    return when (this) {
        is Fail -> this.locale == locale
        else -> false
    }
}

@Composable
fun SignupViewState.commonFailMessageOrNull(): String? {
    return failMessageOrNull(locale = Locale.COMMON)
}

@Composable
fun SignupViewState.failMessageOrEmpty(locale: Locale): String {
    return failMessageOrNull(locale) ?: ""
}

@Composable
fun SignupViewState.failMessageOrNull(locale: Locale): String? {
    if (this is Fail) {
        if (this.locale == locale) {
            return stringResource(this.exception.msgResId)
        }
    }

    return null
}
