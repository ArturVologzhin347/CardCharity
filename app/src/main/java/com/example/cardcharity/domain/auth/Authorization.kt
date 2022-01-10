package com.example.cardcharity.domain.auth

import com.example.cardcharity.domain.common.Event
import com.example.cardcharity.repository.model.User
import com.example.cardcharity.utils.extensions.isEmail
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class Authorization @Inject constructor() {
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val firebaseUser
        get() = firebaseAuth.currentUser


    init {
        firebaseUser?.let {
            _user.value = User(it)
        }
    }

    fun resetPassword(oldPassword: String, newPassword: String) {

    }

    fun sendForgotPasswordMail(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            //TODO
        }
    }

    fun signOut() {
        _user.value = null
        firebaseAuth.signOut()
    }

    suspend fun createUser(
        email: String,
        password: String
    ): Event<User> = suspendCoroutine {
        firebaseAuth.createUserWithEmailAndPassword(email, password).handle(it)
    }

    suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Event<User> = suspendCoroutine {
        firebaseAuth.signInWithEmailAndPassword(email, password).handle(it)
    }

    suspend fun loginWithGoogle(
        idToken: String
    ): Event<User> = suspendCoroutine {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).handle(it)
    }

    private fun Task<AuthResult>.handle(continuation: Continuation<Event<User>>) {
        addOnCompleteListener { task ->
            CoroutineScope(Dispatchers.IO).launch {
                continuation.resume(authorize(task))
            }
        }
    }

    private suspend fun authorize(
        result: Task<AuthResult>
    ): Event<User> = suspendCoroutine { continuation ->
        if (!result.isComplete) {
            return@suspendCoroutine
        }

        if (result.isSuccessful) {
            val firebaseUser = firebaseUser
            if (firebaseUser != null) {

                with(User(firebaseUser)) {
                    _user.value = this
                    continuation.resume(Event.success(this))
                }

                Timber.d("Authorized, email: ${firebaseUser.email}")
            }
        } else {
            _user.value = null
            continuation.resume(Event.fail(checkNotNull(result.exception)))
            Timber.w("Authorization failure")
            Timber.w(result.exception)
        }
    }
}