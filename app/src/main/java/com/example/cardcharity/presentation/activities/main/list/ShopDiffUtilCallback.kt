package com.example.cardcharity.presentation.activities.main.list

import androidx.recyclerview.widget.DiffUtil

class ShopDiffUtilCallback(
    private val oldItems: List<ModelShop>,
    private val newItems: List<ModelShop>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]

        if (oldItem.type != newItem.type) {
            return false
        }

        return when (oldItem.type) {
            ModelType.MODEL -> oldItem.shop!!.id == newItem.shop!!.id
            ModelType.TITLE -> oldItem.title == newItem.title
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]

        if (oldItem.type != newItem.type) {
            return false
        }

        return when (oldItem.type) {
            ModelType.MODEL -> {
                val oldShop = oldItem.shop!!
                val newShop = newItem.shop!!
                oldShop.id == newShop.id && oldShop.name == newShop.name
            }

            ModelType.TITLE -> oldItem.title == newItem.title
        }
    }
}