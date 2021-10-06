package com.example.cardcharity.presentation.activities.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.example.cardcharity.R
import com.example.cardcharity.databinding.ActivityMainBinding
import com.example.cardcharity.presentation.activities.about.AboutActivity
import com.example.cardcharity.presentation.activities.result.ResultActivity
import com.example.cardcharity.presentation.activities.settings.SettingsActivity
import com.example.cardcharity.presentation.base.BaseActivity
import com.example.cardcharity.utils.view

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main), Toolbar.OnMenuItemClickListener {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar(binding.toolbar)
        toolbar.setOnMenuItemClickListener(this)
        viewModel.test()

        ResultActivity.show(this, "http://10d0-82-151-196-167.ngrok.io/code/?&shop=2")


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

    private fun showPopupMenu(v: View) {
        val popup = PopupMenu(this, v)
        popup.inflate(R.menu.main_popup)
        popup.setOnMenuItemClickListener {
            //TODO привести в порядок
            return@setOnMenuItemClickListener when (it.itemId) {
                R.id.action_settings -> {
                    val i = Intent(this, SettingsActivity::class.java)
                    startActivity(i)
                    true
                }

                R.id.action_about -> {
                    val i = Intent(this, AboutActivity::class.java)
                    startActivity(i)
                    true
                }
                else -> false
            }
        }

        popup.show()
    }

}