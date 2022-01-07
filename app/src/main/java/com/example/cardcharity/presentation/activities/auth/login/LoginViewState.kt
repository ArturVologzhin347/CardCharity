package com.example.cardcharity.presentation.activities.auth.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.cardcharity.R
import com.example.cardcharity.presentation.ui.UiException

sealed class LoginViewState {

    object Default : LoginViewState()

    object Success : LoginViewState()

    sealed class Fail(
        val locale: Locale,
        val exception: UiException
    ) : LoginViewState() {

        //fails (ui states)
        object EmailEmpty : Fail(Locale.EMAIL, EmailEmptyException)
        object PasswordEmpty : Fail(Locale.PASSWORD, PasswordEmptyException)
        object UserDoesNotExist : Fail(Locale.EMAIL, UserDoesNotExistException)
        object WrongPassword : Fail(Locale.PASSWORD, WrongPasswordException)
        object NoNetworkConnection : Fail(Locale.COMMON, NoNetworkConnectionException)
        object GoogleAuth : Fail(Locale.COMMON, GoogleAuthException)
        object Unknown : Fail(Locale.COMMON, UnknownException)

        //Ui exceptions
        private object EmailEmptyException : UiException(R.string.error_email_empty)
        private object PasswordEmptyException : UiException(R.string.error_password_empty)
        private object WrongPasswordException : UiException(R.string.error_wrong_password)
        private object UserDoesNotExistException : UiException(R.string.error_user_does_not_exist)
        private object NoNetworkConnectionException : UiException(R.string.error_no_network)
        private object GoogleAuthException : UiException(R.string.login_google_failed)
        private object UnknownException : UiException(R.string.error_unknown)

        enum class Locale {
            COMMON,
            EMAIL,
            PASSWORD
        }
    }

    object Load : LoginViewState()

    companion object {

        fun default() = Default

        fun success() = Success

        fun failEmailEmpty() = Fail.EmailEmpty

        fun failPasswordEmpty() = Fail.PasswordEmpty

        fun failWrongPassword() = Fail.WrongPassword

        fun failUserDoesNotExist() = Fail.UserDoesNotExist

        fun failNoNetworkConnection() = Fail.NoNetworkConnection

        fun failGoogleAuth() = Fail.GoogleAuth

        fun failUnknown() = Fail.Unknown

        fun load() = Load

    }
}

data class LoginFailHandled(val fail: LoginViewState.Fail): Exception()

fun LoginViewState.Fail.handled(): LoginFailHandled {
    return LoginFailHandled(this)
}

fun LoginViewState.failOrNot(locale: LoginViewState.Fail.Locale): Boolean {
    return when (this) {
        is LoginViewState.Fail -> this.locale == locale
        else -> false
    }
}

@Composable
fun LoginViewState.failMessageOrEmpty(locale: LoginViewState.Fail.Locale): String {
    if (this is LoginViewState.Fail) {
        if (this.locale == locale) {
            return stringResource(this.exception.msgResId)
        }
    }

    return ""
}

@Composable
fun LoginViewState.commonFailMessageOrNull(): String? {
    if (this is LoginViewState.Fail) {
        if (this.locale == LoginViewState.Fail.Locale.COMMON) {
            return stringResource(this.exception.msgResId)
        }
    }

    return null
}


