package com.example.cardcharity.presentation.base

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import com.example.cardcharity.App
import com.example.cardcharity.di.Dagger2
import com.example.cardcharity.repository.network.Api
import com.example.cardcharity.utils.extensions.api
import com.example.cardcharity.utils.extensions.dagger

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    protected val context: Context
        get() = getApplication<App>()

    protected val api: Api
        get() = context.api

    fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }

    init {
        inject()
    }

    private fun inject() {
        inject(context.dagger)
    }

    protected open fun inject(dagger: Dagger2) {}
}
