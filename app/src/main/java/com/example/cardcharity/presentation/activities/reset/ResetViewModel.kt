package com.example.cardcharity.presentation.activities.reset

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.cardcharity.presentation.activities.auth.signup.SignupFailHandled
import com.example.cardcharity.presentation.base.mvi.MviViewModel
import com.example.cardcharity.utils.extensions.trimmedIsEmpty
import kotlinx.coroutines.launch
import timber.log.Timber

class ResetViewModel(application: Application) :
    MviViewModel<ResetViewState, ResetEvent>(default(), application) {

    override fun reduceEvent(event: ResetEvent) {
        when (event) {
            is ResetPassword -> resetPasswordEvent(event)
            else -> super.reduceEvent(event)
        }
    }

    private fun resetPasswordEvent(event: ResetPassword) = viewModelScope.launch {
        state = load()

        with(event) {
            try {
                validateEmail(email)
                authorization.sendForgotPasswordMail(email) { isSuccessful ->
                    state = if (isSuccessful) success() else failUnknown()
                }

            } catch (handled: ResetPasswordFailHandled) {
                state = handled.fail
            } catch (e: Exception) {
                state = failUnknown()
                Timber.w(e)
            }

        }
    }

    @Throws(SignupFailHandled::class)
    private fun validateEmail(email: String) {
        when {
            email.trimmedIsEmpty() ->
                throw failEmptyEmail().handled()
        }
    }
}
