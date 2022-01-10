package com.example.cardcharity.presentation.activities.password.reset

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.cardcharity.presentation.activities.auth.signup.SignupFailHandled
import com.example.cardcharity.presentation.activities.auth.signup.SignupViewModel
import com.example.cardcharity.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class ResetPasswordViewModel(application: Application) : BaseViewModel(application) {
    private val _viewState = MutableStateFlow<ResetPasswordViewState>(default())
    val viewState = _viewState.asStateFlow()

    var state: ResetPasswordViewState
        get() = viewState.value
        private set(value) {
            _viewState.value = value
            Timber.i("Current state: $state")
        }


    fun reduceEvent(event: ResetPasswordEvent) {
        when (event) {
            is ResetPassword -> resetPasswordEvent(event)
            else -> throw NotImplementedError("Event $event is not implemented in $TAG")
        }
    }

    /*
    private fun signupEvent(event: SignupEvent.Signup) = viewModelScope.launch {
        state = SignupViewState.load()

        try {
            with(event) {
                validateEmail(email)
                validatePassword(password)
                validateConfirm(password, confirm)

                when (val authEvent = authorization.createUser(email, password)) {
                    is Event.Fail -> state = handleFirebaseErrors(authEvent.throwable)
                    is Event.Success -> state = SignupViewState.success()
                    Event.Load -> {} /* Ignore */
                }
            }

        } catch (handled: SignupFailHandled) {
            state = handled.fail
        } catch (e: Exception) {
            state = SignupViewState.failUserAlreadyExists()
            Timber.w(e)
        }
    }
     */
    private fun resetPasswordEvent(event: ResetPassword) = viewModelScope.launch {
        state = load()

        with(event) {
            try {
                validateNewPassword(newPassword)
                validateRepeatPassword(newPassword, repeatPassword)

                //TODO
                //authorization.sendForgotPasswordMail()
            } catch (handled: ResetPasswordFailHandled) {
                state = handled.fail
            } catch (e: Exception) {
                state = failUnknown()
                Timber.w(e)
            }

        }
    }

    @Throws(SignupFailHandled::class)
    private fun validateNewPassword(password: String) {
        when {
            password.trim().length < SignupViewModel.MIN_PASSWORD_LENGTH ->
                throw failNewPasswordInvalid().handled()
        }
    }

    @Throws(SignupFailHandled::class)
    private fun validateRepeatPassword(newPassword: String, repeatPassword: String) {
        when {
            newPassword != repeatPassword ->
                throw failRepeatedPasswordInvalid().handled()
        }
    }

    companion object {
        private const val TAG = "ResetPasswordViewModel"
    }
}