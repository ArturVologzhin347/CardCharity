package com.example.cardcharity.presentation.activities.main

import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardcharity.R
import com.example.cardcharity.databinding.ActivityMainBinding
import com.example.cardcharity.domen.auth.Authentication
import com.example.cardcharity.presentation.activities.settings.SettingsActivity
import com.example.cardcharity.presentation.activities.splash.SplashActivity
import com.example.cardcharity.presentation.anim.HideableManager
import com.example.cardcharity.presentation.base.BaseActivity
import com.example.cardcharity.presentation.component.recyclerview.sticky.StickyHeaderDecoration
import com.example.cardcharity.utils.openActivity
import com.example.cardcharity.utils.view
import jp.wasabeef.recyclerview.animators.LandingAnimator

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    Toolbar.OnMenuItemClickListener {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var hideableManagerMessage: HideableManager
    private lateinit var hideableManagerProgressBar: HideableManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupToolbar(binding.toolbar)
        toolbar.setOnMenuItemClickListener(this)

        initializeAnimators()
        viewModel.initializeRecyclerAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(false)
            val stickyHeaderItemDecoration = StickyHeaderDecoration(this, viewModel.adapter)
            addItemDecoration(stickyHeaderItemDecoration)
            itemAnimator = LandingAnimator()
            adapter = viewModel.adapter
            refresh()
        }

        binding.buttonRefresh.setOnClickListener {
            refresh()
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item != null) {
            if (item.itemId == R.id.action_more) {
                showPopupMenu(item.view(this))
                return true
            }
        }
        return false
    }

    override fun onStart() {
        if (!Authentication.isLoggedIn()) {
            openActivity(this, SplashActivity::class)
            finish()
        }
        super.onStart()
    }


    private fun refresh() {
        hideableManagerProgressBar.show()
        hideableManagerMessage.hide()

        viewModel.requestModelsForRecyclerView(object : MainViewModel.RequestCallback {
            override fun onSuccessful() {}

            override fun onFailure() {
                hideableManagerMessage.show()
            }

            override fun onComplete() {
                hideableManagerProgressBar.hide()
            }
        })
    }

    private fun initializeAnimators() {
        hideableManagerMessage = HideableManager(this).apply {
            attach(binding.cardViewMessage)
        }

        hideableManagerProgressBar = HideableManager(this).apply {
            attach(binding.cardViewProgress)
        }
    }

    private fun showPopupMenu(v: View) {
        val wrapper = ContextThemeWrapper(this, R.style.PopupMenu)
        val popup = PopupMenu(wrapper, v)
        popup.inflate(R.menu.main_popup)
        popup.setOnMenuItemClickListener {
            return@setOnMenuItemClickListener when (it.itemId) {
                R.id.action_settings -> {
                    val i = Intent(this, SettingsActivity::class.java)
                    startActivity(i)
                    true
                }

                else -> false
            }
        }

        popup.show()
    }
}