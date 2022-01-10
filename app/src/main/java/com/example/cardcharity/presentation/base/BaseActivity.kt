package com.example.cardcharity.presentation.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import com.example.cardcharity.R
import com.example.cardcharity.di.Dagger2
import com.example.cardcharity.presentation.theme.CardCharityTheme
import com.example.cardcharity.presentation.theme.DarkThemeManager
import com.example.cardcharity.repository.preferences.Preferences
import com.example.cardcharity.utils.extensions.dagger
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import javax.inject.Inject

abstract class BaseActivity : ComponentActivity() {

    @Inject
    lateinit var themeManager: DarkThemeManager

    protected open fun inject(dagger: Dagger2) {
        dagger.repositoryComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject(dagger)
        super.onCreate(savedInstanceState)

        setContent {
            LocalTheme {
                Screen()
            }
        }

    }

    @Composable
    protected abstract fun Screen()

    @Composable
    private fun LocalTheme(content: @Composable () -> Unit) {
        val theme = themeManager.theme.collectAsState().value
        val darkTheme = themeManager.isDarkTheme(theme)

        CardCharityTheme(
            darkTheme = darkTheme,
            content = content
        )
    }
}
