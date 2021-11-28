package com.example.cardcharity.presentation.activities.welcome

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.activity.viewModels
import com.example.cardcharity.R
import com.example.cardcharity.databinding.ActivityWelcomeBinding
import com.example.cardcharity.presentation.base.BaseActivity
import com.example.cardcharity.utils.attr


class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>(R.layout.activity_welcome) {
    private val viewModel: WelcomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.nextButton.setOnClickListener {
            viewModel.next(this)
        }


    }



}