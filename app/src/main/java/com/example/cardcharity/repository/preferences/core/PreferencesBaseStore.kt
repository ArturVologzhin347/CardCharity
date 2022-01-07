package com.example.cardcharity.repository.preferences.core

import android.content.SharedPreferences
import java.math.BigDecimal
import java.math.BigInteger

abstract class PreferencesBaseStore(storeName: String, preferences: SharedPreferences) :
    PreferencesPrimaryStore(storeName, preferences) {

    fun setByte(key: String, value: Byte) {
        edit { it.putInt(key, value.toInt()) }
    }

    fun setShort(key: String, value: Short) {
        edit { it.putInt(key, value.toInt()) }
    }

    fun setDouble(key: String, value: Double) {
        edit { it.putString(key, value.toString()) }
    }

    fun setBigDecimal(key: String, value: BigDecimal) {
        edit { it.putString(key, value.toPlainString()) }
    }

    fun setBigInteger(key: String, value: BigInteger) {
        edit { setString(key, value.toString()) }
    }

    fun <T : Enum<T>> setEnum(key: String, value: T) {
        edit { it.putString(key, value.name) }
    }

    fun getByte(key: String, defaultValue: Byte = 0): Byte {
        apply {

        }
        return preferences.getInt(key, defaultValue.toInt()).toByte()
    }

    fun getShort(key: String, defaultValue: Short = 0): Short {
        return preferences.getInt(key, defaultValue.toInt()).toShort()
    }

    fun getDouble(key: String, defaultValue: Double = 0.0): Double {
        return preferences.getString(key, defaultValue.toString())!!.toDouble()
    }

    fun getBigDecimal(key: String, defaultValue: BigDecimal = BigDecimal.ZERO): BigDecimal {
        return BigDecimal(preferences.getString(key, defaultValue.toPlainString()))
    }

    fun getBigInteger(key: String, defaultValue: BigInteger = BigInteger.ZERO): BigInteger {
        return BigInteger(getString(key, defaultValue.toString()) ?: emptyException(key))
    }

    inline fun <reified T : Enum<T>> getEnum(key: String): T {
        return enumValueOf(getString(key))
    }

    fun set(value: Any?, key: String) {
        if (value == null) {
            remove(key)
            return
        }

        set(value, key)
    }

    override fun set(key: String, value: Any) {
        when (value) {
            is Double -> setDouble(key, value)
            is Byte -> setByte(key, value)
            is Short -> setShort(key, value)
            is BigDecimal -> setBigDecimal(key, value)
            is BigInteger -> setBigInteger(key, value)
            else -> super.set(key, value)
        }
    }

    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    override fun <T : Any> get(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is Double -> getDouble(key, defaultValue)
            is Byte -> getByte(key, defaultValue)
            is Short -> getShort(key, defaultValue)
            is BigDecimal -> getBigDecimal(key, defaultValue)
            is BigInteger -> getBigInteger(key, defaultValue)
            else -> super.get(key, defaultValue)
        } as T
    }
}

