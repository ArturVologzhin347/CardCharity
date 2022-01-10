package com.example.cardcharity.repository.model

import android.net.Uri
import com.google.firebase.auth.FirebaseUser

data class User(
    val uid: String,
    val email: String,
    val name: String?,
    val photoUrl: Uri?,
    val createdAt: Long
) {
    constructor(firebaseUser: FirebaseUser) : this(
        uid = firebaseUser.uid,
        email = checkNotNull(firebaseUser.email) {
            "Email is required, check your firebase auth options"
        },
        name = firebaseUser.displayName,
        photoUrl = firebaseUser.photoUrl,
        createdAt = firebaseUser.metadata?.creationTimestamp ?: System.currentTimeMillis()
    )
}
