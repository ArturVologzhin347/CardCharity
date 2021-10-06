package com.example.cardcharity.presentation.activities.about

import android.os.Bundle
import androidx.activity.viewModels
import com.example.cardcharity.R
import com.example.cardcharity.databinding.ActivityAboutBinding
import com.example.cardcharity.presentation.base.BaseActivity

class AboutActivity : BaseActivity<ActivityAboutBinding>(R.layout.activity_about) {
    private val viewModel: AboutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar(binding.toolbar)
        setDefaultNavigationAction()

    }
}