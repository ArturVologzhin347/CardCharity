package com.example.cardcharity.domen.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import timber.log.Timber

/*
TODO
- sign out button
- refactor NetworkService
- profile avatar
https://firebase.google.com/docs/auth/android/google-signin
 */

object Authentication {
    private val firebaseAuth: FirebaseAuth by lazy { Firebase.auth }

    private val firebaseUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    fun isLoggedIn(): Boolean {
        return firebaseUser != null
    }

    fun firebaseAuthWithGoogle(idToken: String, callback: LoginCallback) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Timber.d("Google login successful")
                if (isLoggedIn()) {
                    callback.onLogin()
                }
            } else {
                Timber.w("Google login failure")
                callback.onFailure(it.exception!!)
            }
        }
    }

    interface LoginCallback {
        fun onLogin()
        fun onFailure(e: Exception)
    }


    val uid: String
        get() = firebaseUser!!.uid

    fun logout() {
        firebaseAuth.signOut()
    }
}