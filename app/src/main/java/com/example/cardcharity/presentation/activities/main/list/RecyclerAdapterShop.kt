package com.example.cardcharity.presentation.activities.main.list

import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cardcharity.R
import com.example.cardcharity.domen.BindingInflater
import com.example.cardcharity.presentation.activities.main.list.holder.ViewHolderShop
import com.example.cardcharity.presentation.activities.main.list.holder.ViewHolderTitle
import com.example.cardcharity.presentation.base.BaseRecyclerAdapter
import com.example.cardcharity.presentation.component.recyclerview.sticky.StickyHeaderDecoration

class RecyclerAdapterShop :
    BaseRecyclerAdapter<ModelShop, ViewHolderModelShop<*>>(),
    StickyHeaderDecoration.StickyHeaderInterface<ModelShop, ViewHolderTitle> {
    private var mModels = listOf<ModelShop>()

    override fun onCreateViewHolder(
        inflater: BindingInflater,
        viewType: Int
    ): ViewHolderModelShop<*> {
        return when (ModelType.values()[viewType]) {
            ModelType.MODEL ->
                ViewHolderShop(inflater.binding(R.layout.activity_main_list_item_shop))

            ModelType.TITLE ->
                ViewHolderTitle(inflater.binding(R.layout.activity_main_list_item_title))
        }
    }

    override fun onBindViewHolder(holder: ViewHolderModelShop<*>, position: Int, item: ModelShop) {
        holder.bind(item)
    }


    fun updateModels(newModels: List<ModelShop>) {
        val diffUtilCallback = ShopDiffUtilCallback(mModels, newModels)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        mModels = newModels
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return mModels.size
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type.ordinal
    }

    override fun getItem(position: Int): ModelShop {
        return mModels[position]
    }

    override fun getHeaderPositionForItem(itemPosition: Int): Int {
        var position = itemPosition
        var headerPosition = 0
        do {
            if (isHeader(position)) {
                headerPosition = position
                break
            }
            position -= 1
        } while (position >= 0)
        return headerPosition
    }


    override fun isHeader(itemPosition: Int): Boolean {
        return getItem(itemPosition).type == ModelType.TITLE
    }

    override fun getHeaderViewType(headerPosition: Int): Int {
        return getItemViewType(headerPosition)
    }

    override fun getHeaderItem(headerPosition: Int): ModelShop {
        return getItem(headerPosition)
    }

    override fun createHeader(
        context: Context,
        parent: RecyclerView,
        viewType: Int
    ): ViewHolderTitle {
        return ViewHolderTitle(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.activity_main_list_item_title,
                parent,
                false
            )
        )
    }

    override fun bindHeader(header: ViewHolderTitle, headerPosition: Int) {
        header.bind(getItem(headerPosition))
    }
}