package com.example.cardcharity.presentation.activities.auth.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.core.os.postDelayed
import androidx.lifecycle.lifecycleScope
import com.example.cardcharity.presentation.activities.auth.signup.SignupActivity
import com.example.cardcharity.presentation.activities.main.MainActivity
import com.example.cardcharity.presentation.activities.reset.ResetActivity
import com.example.cardcharity.presentation.base.mvi.MviActivity
import com.example.cardcharity.utils.extensions.launchWhenStarted
import com.example.cardcharity.utils.extensions.openActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.flow.onEach

class LoginActivity : MviActivity<LoginViewState, LoginEvent, LoginViewModel>() {
    override val viewModel: LoginViewModel by viewModels()

    private val googleSignInClient: GoogleSignInClient by lazy(LazyThreadSafetyMode.NONE) {
        GoogleSignIn.getClient(this, viewModel.gso)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.viewState.onEach { state ->
            if (state is Success) reduceEvent(go())
        }.launchWhenStarted(lifecycleScope)
    }

    @Composable
    override fun Screen(reduce: (event: LoginEvent) -> Unit, viewState: LoginViewState) {
        LoginScreen(
            reduce = reduce,
            viewState = viewState
        )
    }

    override fun reduceEvent(event: LoginEvent) {
        if (viewModel.state == Load) return

        when (event) {
            is ForgotPassword -> forgotPasswordEvent(event.email)
            LoginWithGoogle -> loginWithGoogleEvent()
            Signup -> signup()
            Go -> goEvent()
            else -> super.reduceEvent(event)
        }
    }

    private fun loginWithGoogleEvent() {
        val signInIntent = googleSignInClient.signInIntent
        googleLoginLauncher.launch(signInIntent)
    }

    private fun forgotPasswordEvent(email: String) {
        ResetActivity.start(this, email)
    }

    private fun signup() {
        openActivity(this, SignupActivity::class)
    }

    private fun goEvent() {
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
