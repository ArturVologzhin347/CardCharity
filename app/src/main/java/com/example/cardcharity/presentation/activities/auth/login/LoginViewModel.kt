package com.example.cardcharity.presentation.activities.auth.login

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.cardcharity.R
import com.example.cardcharity.domain.common.Event
import com.example.cardcharity.presentation.base.mvi.MviViewModel
import com.example.cardcharity.utils.extensions.trimmedIsEmpty
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(application: Application) :
    MviViewModel<LoginViewState, LoginEvent>(default(), application) {

    val gso: GoogleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).apply {
            requestIdToken(getString(R.string.web_client_id))
            requestEmail()
        }.build()


    override fun reduceEvent(event: LoginEvent) {
        when (event) {
            is LoginWithEmailAndPassword -> loginWithEmailAndPasswordEvent(event)
            else -> super.reduceEvent(event)
        }
    }

    private fun loginWithEmailAndPasswordEvent(
        event: LoginWithEmailAndPassword
    ) = viewModelScope.launch(Dispatchers.IO) {
        try {
            with(event) {
                validateEmail(email)
                validatePassword(password)

                state = when (val authEvent =
                    authorization.loginWithEmailAndPassword(email, password)) {
                    is Event.Fail -> handleFirebaseErrors(authEvent.throwable)
                    is Event.Success -> success()
                    Event.Load -> load()
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
            email.trimmedIsEmpty() -> throw failEmailEmpty().handled()
        }
    }

    @Throws(LoginFailHandled::class)
    private fun validatePassword(password: String) {
        when {
            password.trimmedIsEmpty() -> throw failPasswordEmpty().handled()
        }
    }

    private fun handleFirebaseErrors(throwable: Throwable): Fail {
        when (throwable) {
            is FirebaseNetworkException -> failNoNetworkConnection()
            is FirebaseAuthException -> when (throwable.errorCode) {
                "ERROR_USER_NOT_FOUND", "ERROR_INVALID_EMAIL" -> failUserDoesNotExist()
                "ERROR_WRONG_PASSWORD" -> failWrongPassword()
                else -> null
            }
            else -> null
        }?.let { return it }

        return failUnknown()
    }

    fun loginWithGoogle(
        task: Task<GoogleSignInAccount>
    ) = viewModelScope.launch {
        state = try {
            val account = checkNotNull(task.getResult(ApiException::class.java))
            val idToken = checkNotNull(account.idToken)

            when (val event = authorization.loginWithGoogle(idToken)) {
                is Event.Fail -> throw event.throwable
                is Event.Success -> success()
                Event.Load -> load()
            }

        } catch (e: Exception) {
            Timber.w(e)
            failGoogleAuth()
        }
    }
}
