package com.example.cardcharity.repository.model

import android.net.Uri
import com.google.firebase.auth.FirebaseUser

data class User(
    val uid: String,
    val email: String?,
    val name: String?,
    val photoUrl: Uri? = Uri.parse("https://via.placeholder.com/300x300")//TODO
) {
    constructor(firebaseUser: FirebaseUser) : this(
        uid = firebaseUser.uid,
        email = firebaseUser.email,
        name = firebaseUser.displayName,
        photoUrl = firebaseUser.photoUrl
    )
}