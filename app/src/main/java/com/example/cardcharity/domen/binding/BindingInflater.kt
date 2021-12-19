package com.example.cardcharity.domen.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

class BindingInflater(container: ViewGroup) {
    private val mContainer = container
    private val mContext = container.context
    private val mLayoutInflater = LayoutInflater.from(mContext)

    fun view(@LayoutRes layoutResId: Int): View {
        return mLayoutInflater.inflate(layoutResId, mContainer, false)
    }

    fun <VDB : ViewDataBinding> binding(@LayoutRes layoutResId: Int): VDB {
        return DataBindingUtil.inflate(mLayoutInflater, layoutResId, mContainer, false)
    }
}
