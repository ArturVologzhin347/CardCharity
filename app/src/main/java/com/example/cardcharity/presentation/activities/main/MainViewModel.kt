package com.example.cardcharity.presentation.activities.main

import android.app.Application
import com.example.cardcharity.presentation.base.BaseViewModel
import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.repository.network.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import timber.log.Timber



class MainViewModel(application: Application) : BaseViewModel(application) {

    fun test() {
        NetworkService.instance.getBarcodeApi().getImage(1).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Timber.e(response.toString())
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
               Timber.e(t)
            }

        })
    }
}