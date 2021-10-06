package com.example.cardcharity.repository.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Shop() : Parcelable {
    @SerializedName("id")
    @Expose
    private var _id: Int? = null

    @SerializedName("name")
    @Expose
    private var _name: String? = null

    @SerializedName("image")
    @Expose
    private var _imageUrl: String? = null

    var id: Int
        get() = _id!!
        set(value) {
            _id = value
        }

    var name: String
        get() = _name!!
        set(value) {
            _name = value
        }

    var imageUrl: String
        get() = _imageUrl!!
        set(value) {
            _imageUrl = value
        }

    constructor(parcel: Parcel) : this() {
        _id = parcel.readValue(Int::class.java.classLoader) as? Int
        _name = parcel.readString()
        _imageUrl = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(_id)
        parcel.writeString(_name)
        parcel.writeString(_imageUrl)
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