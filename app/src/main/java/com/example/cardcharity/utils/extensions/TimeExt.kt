package com.example.cardcharity.utils.extensions

import android.content.Context
import com.example.cardcharity.domen.time.DayTimeState
import java.util.*

fun getCurrentHour(): Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)


fun getCurrentDayTimeState(): DayTimeState {
    return when (getCurrentHour()) {
        in 5..11 -> DayTimeState.MORNING
        in 12..15 -> DayTimeState.AFTERNOON
        in 16..20 -> DayTimeState.EVENING
        in 21..23, in 0..4 -> DayTimeState.NIGHT
        else -> DayTimeState.DEFAULT
    }
}

fun getGreeting(context: Context, state: DayTimeState, name: String): String {
    return "${context.getString(state.nameResId)},\n$name!"
}

