package com.example.cardcharity.presentation.activities.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.cardcharity.R
import com.example.cardcharity.databinding.ActivityAuthBinding
import com.example.cardcharity.domen.auth.Authentication
import com.example.cardcharity.presentation.activities.main.MainActivity
import com.example.cardcharity.presentation.base.BaseActivity
import com.example.cardcharity.utils.openActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class AuthActivity : BaseActivity<ActivityAuthBinding>(R.layout.activity_auth) {
    private val viewModel: AuthViewModel by viewModels()

    private val googleLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                Authentication.firebaseAuthWithGoogle(
                    account.idToken!!,
                    object : Authentication.LoginCallback {
                        override fun onLogin() {
                            openActivity(this@AuthActivity, MainActivity::class)
                        }

                        override fun onFailure(e: Exception) {
                            failureLogin(e)
                        }
                    })
            } catch (e: ApiException) {
                failureLogin(e)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.createGso()

        binding.loginWithGoogle.setOnClickListener {
            loginWithGoogle()
        }
    }

    private fun loginWithGoogle() {
        val intent = viewModel.googleSignInClient.signInIntent
        googleLauncher.launch(intent)
    }

    private fun failureLogin(e: Exception) {
        Timber.w("Google sign is failed", e)
        Snackbar.make(binding.root, "Google sign is failed", Snackbar.LENGTH_LONG).show()
    }
}