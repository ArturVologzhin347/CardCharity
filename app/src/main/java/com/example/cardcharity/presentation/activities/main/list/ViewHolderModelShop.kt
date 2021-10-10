package com.example.cardcharity.presentation.activities.main.list

import androidx.databinding.ViewDataBinding
import com.example.cardcharity.presentation.base.BaseViewHolder

abstract class ViewHolderModelShop<VDB : ViewDataBinding>(binding: VDB) :
    BaseViewHolder<VDB>(binding) {

    abstract fun bind(model: ModelShop)
}