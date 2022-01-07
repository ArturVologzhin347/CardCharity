package com.example.cardcharity.presentation.activities.auth.login

import android.app.Activity
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class LoginViewModel(application: Application) : BaseViewModel(application) {
    private val _viewState = MutableStateFlow<LoginViewState>(LoginViewState.default())
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

    @Inject
    lateinit var authorization: Authorization

    override fun inject(dagger: Dagger2) {
        super.inject(dagger)
        dagger.repositoryComponent.inject(this)
    }

    fun reduceEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.LoginWithEmailAndPassword -> loginWithEmailAndPasswordEvent(event)
            else -> throw NotImplementedError("Event $event is not implemented in $TAG")
        }
    }

    private fun loginWithEmailAndPasswordEvent(
        event: LoginEvent.LoginWithEmailAndPassword
    ) = viewModelScope.launch {
        state = LoginViewState.load()

        try {
            with(event) {
                validateEmail(email)
                validatePassword(password)

                //authorization.loginWithEmailAndPassword(email, password)
                when(val authEvent = authorization.loginWithEmailAndPassword(email, password)) {
                    is Event.Fail -> {
                        Timber.e(authEvent.throwable)
                        state = LoginViewState.default()
                    }

                    is Event.Success -> state = LoginViewState.success()
                    Event.Load -> {} /* Ignore */
                }


            }
        } catch (handled: LoginFailHandled) {
            state = handled.fail
        } catch (e: Exception) {
            state = LoginViewState.failNoNetworkConnection()
            Timber.w(e)
        }
    }

    @Throws(LoginFailHandled::class)
    private fun validateEmail(email: String) {
        when {
            email.trimmedIsEmpty() ->
                throw LoginViewState.failEmailEmpty().handled()
        }
    }

    @Throws(LoginFailHandled::class)
    private fun validatePassword(password: String) {
        when {
            password.trimmedIsEmpty() ->
                throw LoginViewState.failPasswordEmpty().handled()
        }
    }

    fun loginWithGoogle(
        task: Task<GoogleSignInAccount>
    ) = viewModelScope.launch {
        state = LoginViewState.load()

        try {
            val account = checkNotNull(task.getResult(ApiException::class.java))
            val idToken = checkNotNull(account.idToken)
            when (val event = authorization.loginWithGoogle(idToken)) {
                is Event.Fail -> throw event.throwable
                is Event.Success -> state = LoginViewState.success()
                Event.Load -> {} /* Ignore */
            }

        } catch (e: Exception) {
            Timber.w(e)
            state = LoginViewState.failGoogleAuth()
        }
    }


    companion object {
        private const val TAG = "LoginViewModel"
    }

}
