package com.example.cardcharity.repository.model

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.example.cardcharity.utils.checkNotNull
import com.google.firebase.auth.FirebaseUser

data class User(
    val email: String,
    val uid: String,
    val name: String,
    val avatar: Uri
) : Parcelable {

    //TODO
    constructor(firebaseUser: FirebaseUser) : this(
        email = firebaseUser.email.checkNotNull(),
        name = firebaseUser.displayName.checkNotNull(),
        avatar = firebaseUser.photoUrl.checkNotNull(),
        uid = firebaseUser.uid
    )

    constructor(parcel: Parcel) : this(
        checkNotNull(parcel.readString()),
        checkNotNull(parcel.readString()),
        checkNotNull(parcel.readString()),
        checkNotNull(parcel.readParcelable(Uri::class.java.classLoader))
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(uid)
        parcel.writeString(name)
        parcel.writeParcelable(avatar, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}