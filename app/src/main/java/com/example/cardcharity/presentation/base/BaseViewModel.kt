package com.example.cardcharity.presentation.base

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import com.example.cardcharity.App

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val context: Context
        get() = getApplication<App>()


    fun getString(@StringRes strResId: Int): String {
        return context.getString(strResId)
    }
}