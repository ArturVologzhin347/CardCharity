package com.example.cardcharity.presentation.activities.settings

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.cardcharity.R
import com.example.cardcharity.databinding.ActivitySettingsBinding
import com.example.cardcharity.presentation.appearence.ThemeController
import com.example.cardcharity.presentation.base.BaseActivity
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : BaseActivity<ActivitySettingsBinding>(R.layout.activity_settings) {
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar(binding.toolbar)
        setDefaultNavigationAction()

        invalidate()
        binding.switchNightMode.setOnCheckedChangeListener { _, isChecked ->
            viewModel.nightModeSwitcher(isChecked) {
                runThemeControllerInvalidate()
            }
        }

        binding.switchNightModeAuto.setOnCheckedChangeListener { _, isChecked ->
            viewModel.nightModeAutoSwitcher(isChecked) {
                invalidate()//Refresh UI
                runThemeControllerInvalidate()
            }
        }
    }

    private fun runThemeControllerInvalidate() {
        runAfterSwitchAnimation {
            ThemeController.invalidate(this)
        }
    }

    private fun runAfterSwitchAnimation(r: Runnable) {
        handler.postDelayed(r, 200)
    }

    private fun invalidate() {
        val themeState = viewModel.themeState
        (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q).let {
            binding.switchNightModeAuto.isEnabled = it
        }

        (themeState == ThemeController.ThemeState.AUTO).let {
            binding.apply {
                switchNightModeAuto.isChecked = it

                switchNightMode.isEnabled = !it

                //Удалить, если нужно всегда отображать night mode switch
                containerNightMode.isVisible = !it

                switchNightMode.isChecked = if (!it) {
                    themeState == ThemeController.ThemeState.NIGHT
                } else {
                    ThemeController.getSystemThemeIsNight(this@SettingsActivity)
                }
            }
        }
    }

    companion object {
        private val handler = Handler(Looper.getMainLooper())
    }
}