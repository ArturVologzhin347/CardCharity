package com.example.cardcharity.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

class BindingInflater(container: ViewGroup) {
    private val mContainer: ViewGroup = container
    private val mContext: Context = mContainer.context
    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)

    fun view(@LayoutRes layoutResId: Int): View {
        return mLayoutInflater.inflate(layoutResId, mContainer, false)
    }

    fun <VDB : ViewDataBinding> binding(@LayoutRes layoutResId: Int): VDB {
        return DataBindingUtil.inflate(mLayoutInflater, layoutResId, mContainer, false)
    }
}
