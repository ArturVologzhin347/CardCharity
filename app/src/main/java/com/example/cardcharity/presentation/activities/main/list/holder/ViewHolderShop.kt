package com.example.cardcharity.presentation.activities.main.list.holder

import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cardcharity.databinding.ActivityMainListItemShopBinding
import com.example.cardcharity.presentation.activities.main.list.ModelShop
import com.example.cardcharity.presentation.activities.main.list.ModelType
import com.example.cardcharity.presentation.activities.main.list.ViewHolderModelShop
import com.example.cardcharity.presentation.activities.main.qr.QrBottomSheet

class ViewHolderShop(binding: ActivityMainListItemShopBinding) :
    ViewHolderModelShop<ActivityMainListItemShopBinding>(binding) {
    override fun bind(model: ModelShop) {
        if (model.type != ModelType.MODEL) {
            throw IllegalArgumentException("Model must be type MODEL")
        }

        val shop = model.shop!!
        binding.apply {
            textViewName.text = shop.name

            try {
                Glide.with(context)
                    .load(shop.imageUrl)
                    .circleCrop()
                    .into(imageView)


            } catch (e: Exception) {
                e.printStackTrace()
            }

            card.setOnClickListener {
                val fm = (context as AppCompatActivity).supportFragmentManager
                QrBottomSheet.display(fm, shop)
            }
        }

    }
}