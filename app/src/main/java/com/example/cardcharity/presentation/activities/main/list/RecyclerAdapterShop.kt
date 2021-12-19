package com.example.cardcharity.presentation.activities.main.list

import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cardcharity.R
import com.example.cardcharity.domen.binding.BindingInflater

import com.example.cardcharity.presentation.base.BaseRecyclerAdapter
import com.example.cardcharity.presentation.component.recyclerview.sticky.StickyHeaderDecoration

class RecyclerAdapterShop(private val uid: String) :
    BaseRecyclerAdapter<ModelShop, ViewHolderModelShop<*>>(),
    StickyHeaderDecoration.StickyHeaderInterface<ModelShop, ViewHolderModelShop.ViewHolderTitle> {
    private var models = listOf<ModelShop>()

    override fun onCreateViewHolder(
        inflater: BindingInflater,
        viewType: Int
    ): ViewHolderModelShop<*> {
        return when (ModelType.values()[viewType]) {
            ModelType.MODEL ->
                ViewHolderModelShop.ViewHolderShop(inflater.binding(R.layout.activity_main_list_item_shop))

            ModelType.TITLE ->
                ViewHolderModelShop.ViewHolderTitle(inflater.binding(R.layout.activity_main_list_item_title))
        }
    }

    override fun onBindViewHolder(holder: ViewHolderModelShop<*>, position: Int, item: ModelShop) {
        holder.bind(item, uid)
    }

    fun setModels(data: List<ModelShop>?) {
        val models = data ?: emptyList()
        val diffUtilCallback = ShopDiffUtilCallback(this.models, models)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        this.models = models
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type.ordinal
    }

    override fun getItem(position: Int): ModelShop {
        return models[position]
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
    ): ViewHolderModelShop.ViewHolderTitle {
        return ViewHolderModelShop.ViewHolderTitle(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.activity_main_list_item_title,
                parent,
                false
            )
        )
    }

    override fun bindHeader(header: ViewHolderModelShop.ViewHolderTitle, headerPosition: Int) {
        header.bind(getItem(headerPosition), uid)
    }
}