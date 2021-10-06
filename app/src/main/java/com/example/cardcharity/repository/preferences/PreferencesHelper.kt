package com.example.cardcharity.repository.preferences

import android.content.SharedPreferences
import androidx.core.content.edit

class PreferencesHelper (key: String, preferences: SharedPreferences) {
    private val mPreferences = preferences
    val preferencesKey = key

    fun setString(key: String, item: String?) {
        edit { it.putString(key, item) }
    }

    fun setInt(key: String, item: Int) {
        edit { it.putInt(key, item) }
    }

    fun setFloat(key: String, item: Float) {
        edit { it.putFloat(key, item) }
    }

    fun setBoolean(key: String, item: Boolean) {
        edit { it.putBoolean(key, item) }
    }

    fun setHashString(key: String, set: Set<String>) {
        edit { it.putStringSet(key, set) }
    }

    fun setArrayString(key: String, set: ArrayList<String>) {
        setHashString(key, HashSet(set))
    }

    fun getString(key: String): String {
        return getString(key, null) ?: nullValueError(key)
    }

    fun getInt(key: String): Int {
        return getInt(key, -1)
    }

    fun getFloat(key: String): Float {
        return getFloat(key, -1F)
    }

    fun getBoolean(key: String): Boolean {
        return getBoolean(key, false)
    }

    fun getString(key: String, default: String?): String? {
        return mPreferences.getString(key, default)
    }

    fun getInt(key: String, default: Int): Int {
        return mPreferences.getInt(key, default)
    }

    fun getFloat(key: String, default: Float): Float {
        return mPreferences.getFloat(key, default)
    }

    fun getBoolean(key: String, default: Boolean): Boolean {
        return mPreferences.getBoolean(key, default)
    }

    fun getHashString(key: String): HashSet<String> {
        return (mPreferences.getStringSet(key, HashSet()) ?: nullValueError(key)) as HashSet<String>
    }

    fun getArrayString(key: String): ArrayList<String> {
        return ArrayList(getHashString(key))
    }

    fun put(keyOfSet: String, newItem: String) {
        val set = getHashString(keyOfSet)
        if (!set.contains(newItem)) {
            set.add(newItem)
            setHashString(keyOfSet, set)
        }
    }

    fun remove(keyOfSet: String, removingItem: String) {
        val set = getHashString(keyOfSet)
        if (set.contains(removingItem)) {
            set.remove(removingItem)
            setHashString(keyOfSet, set)
        }
    }

    fun contains(keyOfSet: String, item: String): Boolean {
        val set = getHashString(keyOfSet)
        return set.contains(item)
    }

    fun get(key: String): Any? {
        return mPreferences.all[key]
    }

    fun subscribe(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        mPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unsubscribe(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        mPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun containsKey(key: String): Boolean {
        return mPreferences.contains(key)
    }

    fun getAllPreferencesKeys(): MutableSet<String> {
        return mPreferences.all.keys
    }

    fun getAll(): Map<String, Any?> {
        return mPreferences.all
    }

    private fun nullValueError(keyProperty: String): Nothing {
        throw NullPointerException("Value in preferences($preferencesKey) " +
                "with tag($keyProperty) is null.")
    }

    private fun edit(action: (SharedPreferences.Editor) -> Unit) {
        mPreferences.edit { action(this) }
    }
}