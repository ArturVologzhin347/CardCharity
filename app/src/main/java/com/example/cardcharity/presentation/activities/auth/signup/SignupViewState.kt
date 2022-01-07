package com.example.cardcharity.presentation.activities.auth.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.cardcharity.R
import com.example.cardcharity.presentation.ui.UiException

sealed class SignupViewState {

    object Default : SignupViewState()

    object Success : SignupViewState()

    object Load : SignupViewState()

    sealed class Fail(
        val locale: Locale,
        val exception: UiException
    ) : SignupViewState() {

        object InvalidEmail : Fail(Locale.EMAIL, EmailInvalidException)
        object InvalidPassword : Fail(Locale.PASSWORD, PasswordInvalidException)
        object PasswordMismatch : Fail(Locale.CONFIRM, PasswordMismatchException)
        object UserAlreadyExists : Fail(Locale.EMAIL, UserAlreadyExistException)
        object Unknown : Fail(Locale.COMMON, UnknownException)

        private object EmailInvalidException : UiException(R.string.error_email_invalid)
        private object PasswordInvalidException : UiException(R.string.password_help)
        private object PasswordMismatchException : UiException(R.string.error_password_mismatch)
        private object UserAlreadyExistException : UiException(R.string.error_user_already_exists)
        private object UnknownException : UiException(R.string.error_unknown)

        enum class Locale {
            COMMON,
            EMAIL,
            PASSWORD,
            CONFIRM
        }
    }

    companion object {

        fun default() = Default

        fun success() = Success

        fun load() = Load

        fun failInvalidEmail() = Fail.InvalidEmail

        fun failInvalidPassword() = Fail.InvalidPassword

        fun failPasswordMismatch() = Fail.PasswordMismatch

        fun failUserAlreadyExists() = Fail.UserAlreadyExists

        fun failUnknown() = Fail.Unknown

    }

}

data class SignupFailHandled(val fail: SignupViewState.Fail) : Exception()

fun SignupViewState.Fail.handled(): SignupFailHandled {
    return SignupFailHandled(this)
}

fun SignupViewState.failOrNot(locale: SignupViewState.Fail.Locale): Boolean {
    return when (this) {
        is SignupViewState.Fail -> this.locale == locale
        else -> false
    }
}

@Composable
fun SignupViewState.commonFailMessageOrNull(): String? {
    return failMessageOrNull(locale = SignupViewState.Fail.Locale.COMMON)
}

@Composable
fun SignupViewState.failMessageOrEmpty(locale: SignupViewState.Fail.Locale): String {
    return failMessageOrNull(locale) ?: ""
}

@Composable
fun SignupViewState.failMessageOrNull(locale: SignupViewState.Fail.Locale): String? {
    if (this is SignupViewState.Fail) {
        if (this.locale == locale) {
            return stringResource(this.exception.msgResId)
        }
    }

    return null
}
