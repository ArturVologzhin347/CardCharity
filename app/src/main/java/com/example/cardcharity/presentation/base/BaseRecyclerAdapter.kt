package com.example.cardcharity.presentation.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cardcharity.domen.binding.BindingInflater

abstract class BaseRecyclerAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {
    private var _bindingInflater: BindingInflater? = null

    private val bindingInflater
        get() = checkNotNull(_bindingInflater)

    abstract fun getItem(position: Int): T

    abstract fun onCreateViewHolder(inflater: BindingInflater, viewType: Int): VH

    abstract fun onBindViewHolder(holder: VH, position: Int, item: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        if (_bindingInflater == null) {
            _bindingInflater = BindingInflater(parent)
        }

        return onCreateViewHolder(bindingInflater, viewType)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        onBindViewHolder(holder, position, item)
    }
}