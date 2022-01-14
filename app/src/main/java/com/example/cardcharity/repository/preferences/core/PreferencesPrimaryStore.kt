package com.example.cardcharity.repository.preferences.core

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.cardcharity.repository.preferences.exceptions.PreferencesEmptyException
import com.example.cardcharity.repository.preferences.exceptions.PreferencesTypeException


abstract class PreferencesPrimaryStore(
    val storeName: String,
    protected val preferences: SharedPreferences
) {

    fun setBoolean(key: String, value: Boolean) {
        edit { it.putBoolean(key, value) }
    }

    fun setString(key: String, value: String?) {
        edit { it.putString(key, value) }
    }

    fun setInt(key: String, value: Int) {
        edit { it.putInt(key, value) }
    }

    fun setLong(key: String, value: Long) {
        edit { it.putLong(key, value) }
    }

    fun setFloat(key: String, value: Float) {
        edit { it.putFloat(key, value) }
    }

    fun setStringSet(key: String, value: Set<String>) {
        edit { it.putStringSet(key, value) }
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    fun getString(key: String): String {
        return getString(key, null) ?: emptyException(key)
    }

    fun getString(key: String, defaultValue: String?): String? {
        return preferences.getString(key, defaultValue)
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return preferences.getInt(key, defaultValue)
    }

    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return preferences.getLong(key, defaultValue)
    }

    fun getFloat(key: String, defaultValue: Float = 0.0F): Float {
        return preferences.getFloat(key, defaultValue)
    }

    fun getStringSet(key: String, defaultValue: Set<String> = emptySet()): Set<String> {
        return preferences.getStringSet(key, defaultValue) ?: emptySet()
    }

    open fun set(key: String, value: Any) {
        when (value) {
            is Boolean -> setBoolean(key, value)
            is String -> setString(key, value)
            is Int -> setInt(key, value)
            is Long -> setLong(key, value)
            is Float -> setFloat(key, value)
            else -> typeException(key, value)
        }
    }

    @Suppress("UNCHECKED_CAST")
    open fun <T : Any> get(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is Boolean -> getBoolean(key, defaultValue)
            is String -> getString(key, defaultValue)
            is Int -> getInt(key, defaultValue)
            is Long -> getLong(key, defaultValue)
            is Float -> getFloat(key, defaultValue)
            else -> typeException(key, defaultValue)
        } as T
    }

    fun contains(key: String): Boolean {
        return preferences.contains(key)
    }

    fun clear() {
        edit { it.clear() }
    }

    fun remove(key: String) {
        edit { it.remove(key) }
    }

    fun subscribe(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unsubscribe(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        preferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun getAllPreferencesKeys(): MutableSet<String> {
        return preferences.all.keys
    }

    fun getAll(): Map<String, Any?> {
        return preferences.all
    }

    override fun toString(): String {
        return getAll().toString()
    }

    protected fun emptyException(key: String): Nothing {
        throw PreferencesEmptyException(storeName, key)
    }

    protected fun typeException(key: String, value: Any): Nothing {
        throw PreferencesTypeException(storeName, key, value)
    }

    protected fun edit(action: (SharedPreferences.Editor) -> Unit) {
        preferences.edit(false) { action(this) }
    }
}
