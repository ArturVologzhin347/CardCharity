package com.example.cardcharity.presentation.activities.auth.signup

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.cardcharity.di.Dagger2
import com.example.cardcharity.domain.auth.Authorization
import com.example.cardcharity.domain.common.Event
import com.example.cardcharity.presentation.base.BaseViewModel
import com.example.cardcharity.utils.extensions.isNotEmail
import com.example.cardcharity.utils.extensions.trimmedIsEmpty
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

    @Inject
    lateinit var authorization: Authorization

    override fun inject(dagger: Dagger2) {
        super.inject(dagger)
        dagger.repositoryComponent.inject(this)
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
                    is Event.Fail -> throw authEvent.throwable //TODO
                    is Event.Success -> SignupViewState.success()
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
            password.length < MIN_PASSWORD_LENGTH ->
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

    companion object {
        private const val TAG = "SignupViewModel"
        private const val MIN_PASSWORD_LENGTH = 6
    }
}
