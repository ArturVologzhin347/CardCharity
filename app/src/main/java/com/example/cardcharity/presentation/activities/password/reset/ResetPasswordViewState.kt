package com.example.cardcharity.presentation.activities.password.reset

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.cardcharity.R

import com.example.cardcharity.presentation.ui.UiException

sealed class ResetPasswordViewState

object Default : ResetPasswordViewState()

object Load : ResetPasswordViewState()

object Success : ResetPasswordViewState()

sealed class Fail(
    val locale: Locale,
    val exception: UiException
) : ResetPasswordViewState() {

    object OldPasswordWrong : Fail(
        Locale.OLD_PASSWORD,
        OldPasswordWrongException
    )

    object NewPasswordInvalid : Fail(
        Locale.NEW_PASSWORD,
        NewPasswordInvalidException
    )

    object RepeatedPasswordInvalid : Fail(
        Locale.REPEAT_NEW_PASSWORD,
        RepeatedPasswordInvalidException
    )

    object Unknown : Fail(
        Locale.COMMON,
        UnknownException
    )

    object OldPasswordWrongException :
        UiException(R.string.error_password_old_wrong)

    object NewPasswordInvalidException :
        UiException(R.string.error_password_old_wrong)

    object RepeatedPasswordInvalidException :
        UiException(R.string.error_repeated_password_invalid)

    object UnknownException :
        UiException(R.string.error_unknown)


    enum class Locale {
        COMMON,
        OLD_PASSWORD,
        NEW_PASSWORD,
        REPEAT_NEW_PASSWORD
    }
}

fun default() = Default

fun load() = Load

fun success() = Success

fun failOldPasswordWrong() = Fail.OldPasswordWrong

fun failNewPasswordInvalid() = Fail.NewPasswordInvalid

fun failRepeatedPasswordInvalid() = Fail.RepeatedPasswordInvalid

fun failUnknown() = Fail.Unknown


data class ResetPasswordFailHandled(val fail: Fail) : Exception()

fun Fail.handled(): ResetPasswordFailHandled {
    return ResetPasswordFailHandled(this)
}

fun ResetPasswordViewState.failOrNot(locale: Fail.Locale): Boolean {
    return when (this) {
        is Fail -> this.locale == locale
        else -> false
    }
}

@Composable
fun ResetPasswordViewState.commonFailMessageOrNull(): String? {
    return failMessageOrNull(locale = Fail.Locale.COMMON)
}

@Composable
fun ResetPasswordViewState.failMessageOrEmpty(locale: Fail.Locale): String {
    return failMessageOrNull(locale) ?: ""
}

@Composable
fun ResetPasswordViewState.failMessageOrNull(locale: Fail.Locale): String? {
    if (this is Fail) {
        if (this.locale == locale) {
            return stringResource(this.exception.msgResId)
        }
    }

    return null
}

