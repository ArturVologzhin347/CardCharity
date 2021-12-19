package com.example.cardcharity.presentation.activities.auth

import android.app.Application
import android.graphics.Point
import com.example.cardcharity.R
import com.example.cardcharity.presentation.base.BaseViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class AuthViewModel(application: Application) : BaseViewModel(application) {
    lateinit var gso: GoogleSignInOptions
    lateinit var googleSignInClient: GoogleSignInClient

    fun createGso() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).apply {
            requestIdToken(getString(R.string.web_client_id))
            requestEmail()
        }.build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)


    }




}