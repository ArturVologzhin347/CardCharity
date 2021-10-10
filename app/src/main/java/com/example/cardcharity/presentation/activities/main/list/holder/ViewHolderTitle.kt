package com.example.cardcharity.presentation.activities.main.list.holder

import com.example.cardcharity.databinding.ActivityMainListItemTitleBinding
import com.example.cardcharity.presentation.activities.main.list.ModelShop
import com.example.cardcharity.presentation.activities.main.list.ModelType
import com.example.cardcharity.presentation.activities.main.list.ViewHolderModelShop

class ViewHolderTitle(binding: ActivityMainListItemTitleBinding) :
    ViewHolderModelShop<ActivityMainListItemTitleBinding>(binding) {

    override fun bind(model: ModelShop) {
        if(model.type != ModelType.TITLE) {
            throw IllegalArgumentException("Model must be type TITLE")
        }

        val title = model.title!!
        binding.textView.text = title



    }
}
