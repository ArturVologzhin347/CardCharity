package com.example.cardcharity.presentation.activities.auth.login

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.cardcharity.R
import com.example.cardcharity.di.Dagger2
import com.example.cardcharity.domain.auth.Authorization
import com.example.cardcharity.domain.common.Event
import com.example.cardcharity.presentation.base.BaseViewModel
import com.example.cardcharity.utils.extensions.trimmedIsEmpty
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class LoginViewModel(application: Application) : BaseViewModel(application) {
    private val _viewState = MutableStateFlow<LoginViewState>(default())
    val viewState = _viewState.asStateFlow()


    var state: LoginViewState
        get() = viewState.value
        private set(value) {
            _viewState.value = value
            Timber.i("Current state: $state")
        }


    val gso: GoogleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).apply {
            requestIdToken(getString(R.string.web_client_id))
            requestEmail()
        }.build()


    fun reduceEvent(event: LoginEvent) {
        when (event) {
            is LoginWithEmailAndPassword -> loginWithEmailAndPasswordEvent(event)
            else -> throw NotImplementedError("Event $event is not implemented in $TAG")
        }
    }

    private fun loginWithEmailAndPasswordEvent(
        event: LoginWithEmailAndPassword
    ) = viewModelScope.launch {
        state = load()

        try {
            with(event) {
                validateEmail(email)
                validatePassword(password)

                when (val authEvent = authorization.loginWithEmailAndPassword(email, password)) {
                    is Event.Fail -> state = handleFirebaseErrors(authEvent.throwable)
                    is Event.Success -> state = success()
                    Event.Load -> {} /* Ignore */
                }
            }
        } catch (handled: LoginFailHandled) {
            state = handled.fail
        } catch (e: Exception) {
            state = failNoNetworkConnection()
            Timber.w(e)
        }
    }

    @Throws(LoginFailHandled::class)
    private fun validateEmail(email: String) {
        when {
            email.trimmedIsEmpty() ->
                throw failEmailEmpty().handled()
        }
    }

    @Throws(LoginFailHandled::class)
    private fun validatePassword(password: String) {
        when {
            password.trimmedIsEmpty() ->
                throw failPasswordEmpty().handled()
        }
    }

    private fun handleFirebaseErrors(throwable: Throwable): Fail {
        return when (throwable) {
            is FirebaseNetworkException -> failNoNetworkConnection()
            is FirebaseAuthException -> when (throwable.errorCode) {
                "ERROR_USER_NOT_FOUND", "ERROR_INVALID_EMAIL" ->
                    failUserDoesNotExist()
                "ERROR_WRONG_PASSWORD" -> failWrongPassword()
                else -> failUnknown()
            }
            else -> failUnknown()
        }
    }

    fun loginWithGoogle(
        task: Task<GoogleSignInAccount>
    ) = viewModelScope.launch {
        state = load()

        try {
            val account = checkNotNull(task.getResult(ApiException::class.java))
            val idToken = checkNotNull(account.idToken)
            when (val event = authorization.loginWithGoogle(idToken)) {
                is Event.Fail -> throw event.throwable
                is Event.Success -> state = success()
                Event.Load -> {} /* Ignore */
            }

        } catch (e: Exception) {
            Timber.w(e)
            state = failGoogleAuth()
        }
    }


    companion object {
        private const val TAG = "LoginViewModel"
    }

}
