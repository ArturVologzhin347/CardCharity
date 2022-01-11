package com.example.cardcharity.presentation.activities.reset

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.cardcharity.R
import com.example.cardcharity.presentation.base.mvi.MviViewState

import com.example.cardcharity.presentation.ui.UiException

sealed class ResetViewState : MviViewState

object Default : ResetViewState()

object Load : ResetViewState()

object Success : ResetViewState()

sealed class Fail(val locale: Locale, val exception: UiException) : ResetViewState()

object EmailEmpty : Fail(Locale.EMAIL, EmailEmptyException)
object Unknown : Fail(Locale.COMMON, UnknownException)

object EmailEmptyException : UiException(R.string.error_email_empty)
object UnknownException : UiException(R.string.error_unknown)

enum class Locale {
    COMMON,
    EMAIL
}


fun default() = Default

fun load() = Load

fun success() = Success

fun failEmptyEmail() = EmailEmpty

fun failUnknown() = Unknown


data class ResetPasswordFailHandled(val fail: Fail) : Exception()

fun Fail.handled(): ResetPasswordFailHandled {
    return ResetPasswordFailHandled(this)
}

fun ResetViewState.failOrNot(locale: Locale): Boolean {
    return when (this) {
        is Fail -> this.locale == locale
        else -> false
    }
}

@Composable
fun ResetViewState.commonFailMessageOrNull(): String? {
    return failMessageOrNull(locale = Locale.COMMON)
}

@Composable
fun ResetViewState.failMessageOrEmpty(locale: Locale): String {
    return failMessageOrNull(locale) ?: ""
}

@Composable
fun ResetViewState.failMessageOrNull(locale: Locale): String? {
    if (this is Fail) {
        if (this.locale == locale) {
            return stringResource(this.exception.msgResId)
        }
    }

    return null
}
