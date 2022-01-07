package com.example.cardcharity.repository.preferences.core

import android.content.Context
import android.content.SharedPreferences

open class PreferencesStore private constructor(
    storeName: String,
    preferences: SharedPreferences
) : PreferencesBaseStore(storeName, preferences) {

    companion object {
        fun of(
            context: Context,
            storeName: String,
            mode: Int = Context.MODE_PRIVATE
        ): PreferencesStore {
            return of(
                storeName = storeName,
                preferences = context.getSharedPreferences(storeName, mode)
            )
        }

        fun of(storeName: String, preferences: SharedPreferences): PreferencesStore {
            return PreferencesStore(storeName, preferences)
        }
    }
}