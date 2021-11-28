package com.example.cardcharity.repository.model

import android.os.Parcel
import android.os.Parcelable
import com.example.cardcharity.repository.network.RetrofitService
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Shop() : Parcelable {
    @SerializedName("id")
    @Expose
    private var _id: Int? = null

    @SerializedName("name")
    @Expose
    private var _name: String? = null

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

    fun getLogoUrl(): String {
        return "${RetrofitService.URL}user/shop/$id/logo"
    }

    fun getCodeUrl(uid: String): String {
        return "${RetrofitService.URL}user/code/?shopId=$id&uid=$uid"
    }

    override fun toString(): String {
        return "Id - $_id \nName - $_name \n"
    }

    constructor(parcel: Parcel) : this() {
        _id = parcel.readValue(Int::class.java.classLoader) as? Int
        _name = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(_id)
        parcel.writeString(_name)
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