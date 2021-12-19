package com.example.cardcharity.domen.time

import androidx.annotation.StringRes
import com.example.cardcharity.R

enum class DayTimeState(@StringRes val nameResId: Int) {
    MORNING(R.string.time_morning),
    AFTERNOON(R.string.time_afternoon),
    EVENING(R.string.time_evening),
    NIGHT(R.string.time_night),
    DEFAULT(R.string.time_default)
}