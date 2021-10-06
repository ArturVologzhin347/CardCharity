package com.example.cardcharity.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.cardcharity.R
import com.example.cardcharity.presentation.appearence.ThemeController

abstract class BaseActivity<VDB : ViewDataBinding>(@LayoutRes private val layoutResId: Int) :
    AppCompatActivity() {
    lateinit var theme: ThemeController.Theme
    protected lateinit var binding: VDB

    private var _toolbar: Toolbar? = null

    val toolbar: Toolbar
        get() = _toolbar!!

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeController.observe(this)
        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        ThemeController.invalidate(this)
        super.onStart()
    }

    open fun themeActivity(): Pair<Int, Int> {
        return Pair(R.style.Theme_CardCharity_Day, R.style.Theme_CardCharity_Night)
    }

    fun setupToolbar(toolbar: Toolbar) {
        _toolbar = toolbar
    }

    fun setDefaultNavigationAction() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    fun invalidateBinding() {
        binding.invalidateAll()
    }
}