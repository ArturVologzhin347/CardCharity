package com.example.cardcharity.repository.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Shop(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
) : Parcelable {

    override fun toString(): String {
        return "${this::class.simpleName} - (id: $id, name: $name)"
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        checkNotNull(parcel.readString())
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Shop> {
        override fun createFromParcel(parcel: Parcel): Shop {
            return Shop(parcel)
        }

        override fun newArray(size: Int): Array<Shop?> {
            return arrayOfNulls(size)
        }
    }
}