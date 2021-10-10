package com.example.cardcharity.presentation.appearence

import androidx.annotation.StyleRes
import com.example.cardcharity.R

interface ThemeActivity {
    var theme: ThemeController.Theme

    fun setTheme(@StyleRes styleResId: Int)
    @StyleRes fun getDayThemeResId(): Int
    @StyleRes fun getNightThemeResId(): Int
}