package com.example.cardcharity.domen.auth

import com.example.cardcharity.repository.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

object Authentication {
    private val firebaseAuth: FirebaseAuth by lazy(LazyThreadSafetyMode.NONE) { Firebase.auth }

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val firebaseUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    val isAuthorized: Boolean
        get() = firebaseUser != null

    init {
        firebaseUser?.let {
            _user.value = User(it)
        }
    }

    fun firebaseAuthWithGoogle(idToken: String, callback: LoginCallback) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            authorize(it).let { isAuthorized ->
                callback.onComplete(isAuthorized)
            }
        }
    }

    private fun authorize(result: Task<AuthResult>): Boolean {
        if (result.isSuccessful) {
            val firebaseUser = this.firebaseUser
            if (firebaseUser != null) {
                _user.value = User(firebaseUser)
                Timber.d("Google login successful")
                return true
            }
        }

        _user.value = null
        Timber.w("Google login failure", result.exception)
        return false
    }

    interface LoginCallback {
        fun onComplete(isAuthorized: Boolean)
    }

    fun logout() {
        firebaseAuth.signOut()
    }
}