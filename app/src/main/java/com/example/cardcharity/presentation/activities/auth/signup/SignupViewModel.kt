package com.example.cardcharity.presentation.activities.auth.signup

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.cardcharity.di.Dagger2
import com.example.cardcharity.domain.auth.Authorization
import com.example.cardcharity.domain.common.Event
import com.example.cardcharity.presentation.base.BaseViewModel
import com.example.cardcharity.utils.extensions.isNotEmail
import com.example.cardcharity.utils.extensions.trimmedIsEmpty
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SignupViewModel(application: Application) : BaseViewModel(application) {
    private val _viewState = MutableStateFlow<SignupViewState>(SignupViewState.default())
    val viewState = _viewState.asStateFlow()

    var state: SignupViewState
        get() = viewState.value
        private set(value) {
            _viewState.value = value
            Timber.i("Current state: $state")
        }

    fun reduceEvent(event: SignupEvent) {
        when (event) {
            is SignupEvent.Signup -> signupEvent(event)
            else -> throw NotImplementedError("Event $event is not implemented in $TAG")
        }
    }

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
            state = SignupViewState.failUnknown()
            Timber.w(e)
        }
    }

    @Throws(SignupFailHandled::class)
    private fun validateEmail(email: String) {
        when {
            email.trimmedIsEmpty() || email.isNotEmail() ->
                throw SignupViewState.failInvalidEmail().handled()
        }
    }

    @Throws(SignupFailHandled::class)
    private fun validatePassword(password: String) {
        when {
            password.trim().length < MIN_PASSWORD_LENGTH ->
                throw SignupViewState.failInvalidPassword().handled()
        }
    }

    @Throws(SignupFailHandled::class)
    private fun validateConfirm(password: String, confirm: String) {
        when {
            password != confirm ->
                throw SignupViewState.failPasswordMismatch().handled()
        }
    }

    private fun handleFirebaseErrors(throwable: Throwable): SignupViewState.Fail {
        return when (throwable) {
            is FirebaseNetworkException -> SignupViewState.failNoNetworkConnection()
            is FirebaseAuthException -> when (throwable.errorCode) {

                "ERROR_INVALID_EMAIL" ->
                    SignupViewState.failInvalidEmail()

                "ERROR_EMAIL_ALREADY_IN_USE", "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" ->
                    SignupViewState.failUserAlreadyExists()

                "ERROR_WEAK_PASSWORD" ->
                    SignupViewState.failInvalidPassword()

                else -> SignupViewState.failUnknown()
            }
            else -> SignupViewState.failUnknown()
        }
    }

    companion object {
        private const val TAG = "SignupViewModel"
        const val MIN_PASSWORD_LENGTH = 6
    }
}
