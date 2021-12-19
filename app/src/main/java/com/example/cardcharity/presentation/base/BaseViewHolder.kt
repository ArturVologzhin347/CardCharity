package com.example.cardcharity.presentation.base

import android.content.Context
import androidx.annotation.StringRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<VDB : ViewDataBinding>(protected val binding: VDB) :
    RecyclerView.ViewHolder(binding.root) {
    protected val context: Context = itemView.context

    fun getString(@StringRes strResId: Int): String {
        return context.getString(strResId)
    }
}