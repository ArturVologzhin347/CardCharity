package com.example.cardcharity.presentation.activities.settings

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.cardcharity.R
import com.example.cardcharity.databinding.ActivitySettingsBinding
import com.example.cardcharity.presentation.appearence.ThemeController
import com.example.cardcharity.presentation.base.BaseActivity
import com.example.cardcharity.presentation.component.dialog.Dialog
import com.example.cardcharity.repository.preferences.Preferences

class SettingsActivity : BaseActivity<ActivitySettingsBinding>(R.layout.activity_settings) {
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar(binding.toolbar)
        setDefaultNavigationAction()

        invalidate()

        binding.apply {
            switchNightMode.setOnCheckedChangeListener { _, isChecked ->
                viewModel.nightModeSwitcher(isChecked) {
                    runThemeControllerInvalidate()
                }
            }

            switchNightModeAuto.setOnCheckedChangeListener { _, isChecked ->
                viewModel.nightModeAutoSwitcher(isChecked) {
                    invalidate()//Refresh UI
                    runThemeControllerInvalidate()
                }
            }

            switchBrightnessBarcode.setOnCheckedChangeListener { _, isChecked ->
                Preferences.barcodeIllumination = isChecked
            }

            buttonSignOut.setOnClickListener {
                Dialog.callSignOut(this@SettingsActivity)
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
        binding.apply {
            val themeState = viewModel.themeState
            (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q).let {
                switchNightModeAuto.isEnabled = it
            }

            (themeState == ThemeController.ThemeState.AUTO).let {
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

            switchBrightnessBarcode.isChecked = Preferences.barcodeIllumination
        }

    }

    companion object {
        private val handler = Handler(Looper.getMainLooper())

        fun start(activity: AppCompatActivity) {
            val i = Intent(activity, SettingsActivity::class.java)
            activity.startActivity(i)
        }
    }
}