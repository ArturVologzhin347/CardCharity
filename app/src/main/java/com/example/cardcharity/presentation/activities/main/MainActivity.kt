package com.example.cardcharity.presentation.activities.main

import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardcharity.R
import com.example.cardcharity.databinding.ActivityMainBinding
import com.example.cardcharity.presentation.activities.settings.SettingsActivity
import com.example.cardcharity.presentation.base.BaseActivity
import com.example.cardcharity.presentation.component.recyclerview.sticky.StickyHeaderDecoration
import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.utils.view
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import jp.wasabeef.recyclerview.animators.LandingAnimator
import retrofit2.Call
import timber.log.Timber


//https://github.com/gongwen/SwipeBackLayout
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    Toolbar.OnMenuItemClickListener {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupToolbar(binding.toolbar)
        toolbar.setOnMenuItemClickListener(this)

        viewModel.initializeRecyclerAdapter()

        binding.recyclerView.apply {
            //Manager
            layoutManager = LinearLayoutManager(this@MainActivity)

            //Dynamic list
            setHasFixedSize(false)

            //Sticky titles
            val stickyHeaderItemDecoration = StickyHeaderDecoration(this, viewModel.adapter)
            addItemDecoration(stickyHeaderItemDecoration)

            //Animations
            itemAnimator = LandingAnimator()

            //Set adapter
            adapter = viewModel.adapter

            //Setup data in recycler view
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

    private fun refresh() {
        viewModel.requestModelsForRecyclerView(object : MainViewModel.RequestCallback {
            override fun onResponse(type: MainViewModel.RequestCallback.Type) {
                Timber.e(type.name)
            }

            override fun onFailure(call: Call<List<Shop>>, t: Throwable) {
                Timber.e(t)
            }
        })
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