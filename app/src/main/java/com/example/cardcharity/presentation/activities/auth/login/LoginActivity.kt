package com.example.cardcharity.presentation.activities.auth.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.core.os.postDelayed
import androidx.lifecycle.lifecycleScope
import com.example.cardcharity.presentation.activities.auth.signup.SignupActivity
import com.example.cardcharity.presentation.activities.main.MainActivity
import com.example.cardcharity.presentation.activities.password.forgot.ForgotActivity
import com.example.cardcharity.presentation.base.BaseActivity
import com.example.cardcharity.utils.extensions.launchWhenStarted
import com.example.cardcharity.utils.extensions.openActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class LoginActivity : BaseActivity() {
    private val viewModel: LoginViewModel by viewModels()

    private val googleSignInClient: GoogleSignInClient by lazy(LazyThreadSafetyMode.NONE) {
        GoogleSignIn.getClient(this, viewModel.gso)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.viewState.onEach { state ->
            if(state is LoginViewState.Success) {
              reduceEvent(LoginEvent.go())
            }
        }.launchWhenStarted(lifecycleScope)
    }

    @Composable
    override fun Screen() {
        val viewState = viewModel.viewState.collectAsState()

        LoginScreen(
            reduce = { event -> reduceEvent(event) },
            viewState = viewState.value
        )
    }

    private fun reduceEvent(event: LoginEvent) {
        Timber.d("Reducing event: $event")

        if (viewModel.state == LoginViewState.Load) {
            Timber.i("Cannot reduce event $event because state is Load")
            return
        }

        when (event) {
            is LoginEvent.ForgotPassword -> forgotPasswordEvent(event.email)
            LoginEvent.LoginWithGoogle -> loginWithGoogleEvent()
            LoginEvent.Signup -> signup()
            LoginEvent.Go -> go()
            else -> viewModel.reduceEvent(event)
        }
    }

    private fun loginWithGoogleEvent() {
        val signInIntent = googleSignInClient.signInIntent
        googleLoginLauncher.launch(signInIntent)
    }

    private fun forgotPasswordEvent(email: String) {
        ForgotActivity.start(this, email)
    }

    private fun signup() {
        openActivity(this, SignupActivity::class)
    }

    private fun go() {
        handler.postDelayed(DELAY_AFTER_GO) {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }

    private val googleLoginLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        with(result) {
            if (resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                viewModel.loginWithGoogle(task)
            }
        }
    }

    companion object {
        private val handler = Handler(Looper.getMainLooper())

        private const val DELAY_AFTER_GO = 200L
    }
}


