package com.example.cardcharity.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.cardcharity.R
import com.example.cardcharity.presentation.appearence.ThemeSupporter
import com.example.cardcharity.presentation.appearence.ThemeController

abstract class BaseActivity<VDB : ViewDataBinding>(@LayoutRes private val layoutResId: Int) :
    AppCompatActivity(), ThemeSupporter {
    override var theme: ThemeController.Theme = ThemeController.Theme.DAY
    protected lateinit var binding: VDB

    private var _toolbar: Toolbar? = null

    val toolbar: Toolbar
        get() = checkNotNull(_toolbar) { "Use setupToolbar()" }

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

    override fun getDayThemeResId(): Int {
        return R.style.Theme_CardCharity_Day
    }

    override fun getNightThemeResId(): Int {
        return R.style.Theme_CardCharity_Night
    }
}