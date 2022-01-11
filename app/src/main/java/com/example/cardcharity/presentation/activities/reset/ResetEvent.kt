package com.example.cardcharity.presentation.activities.reset

import com.example.cardcharity.presentation.base.mvi.MviEvent

sealed class ResetEvent: MviEvent

object Finish : ResetEvent()

data class ResetPassword(val email: String) : ResetEvent()


fun finish() = Finish

fun resetPassword(email: String) = ResetPassword(email)
