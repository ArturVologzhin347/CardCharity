package com.example.cardcharity.presentation.activities.main.list

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.cardcharity.databinding.ActivityMainListItemShopBinding
import com.example.cardcharity.databinding.ActivityMainListItemTitleBinding
import com.example.cardcharity.domen.glide.GlideApp
import com.example.cardcharity.presentation.activities.main.qr.QrBottomSheet
import com.example.cardcharity.presentation.base.BaseViewHolder
import com.example.cardcharity.utils.extensions.getLogoUrl

sealed class ViewHolderModelShop<VDB : ViewDataBinding>(binding: VDB) :
    BaseViewHolder<VDB>(binding) {

    abstract fun bind(model: ModelShop, uid: String)

    class ViewHolderShop(binding: ActivityMainListItemShopBinding) :
        ViewHolderModelShop<ActivityMainListItemShopBinding>(binding) {

        override fun bind(model: ModelShop, uid: String) {
            val shop = model.getShop()

            with(binding) {
                textViewName.text = shop.name

                try {
                    GlideApp.with(context)
                        .load(shop.getLogoUrl())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .circleCrop()
                        .into(imageView)

                } catch (e: Exception) {
                    e.printStackTrace()
                }

                card.setOnClickListener {
                    val fm = (context as AppCompatActivity).supportFragmentManager
                    QrBottomSheet.display(fm, uid, shop)
                }
            }
        }
    }

    class ViewHolderTitle(binding: ActivityMainListItemTitleBinding) :
        ViewHolderModelShop<ActivityMainListItemTitleBinding>(binding) {

        override fun bind(model: ModelShop, uid: String) {
            binding.textView.text = model.geTitle()
        }
    }
}