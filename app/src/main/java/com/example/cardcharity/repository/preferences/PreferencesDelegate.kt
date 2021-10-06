package com.example.cardcharity.repository.preferences

import com.example.cardcharity.App
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class PreferencesDelegate<T>(
    defValue: T,
    preferences: PreferencesHelper = App.preferencesHelper
    ) : ReadWriteProperty<Any?, T> {
    private val mPreferences = preferences
    private val mDefValue = defValue
    private val mode: ImplementationMode = findImplementation()


    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val value: Any? = when(mode) {
            ImplementationMode.BOOLEAN ->
                mPreferences.getBoolean(property.name, mDefValue as Boolean)

            ImplementationMode.INT ->
                mPreferences.getInt(property.name, mDefValue as Int)

            ImplementationMode.STRING ->
                mPreferences.getString(property.name, mDefValue as String)

            ImplementationMode.FLOAT ->
                mPreferences.getFloat(property.name, mDefValue as Float)
        }

        return value as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        when(mode) {
            ImplementationMode.BOOLEAN ->
                mPreferences.setBoolean(property.name, value as Boolean)

            ImplementationMode.INT ->
                mPreferences.setInt(property.name, value as Int)

            ImplementationMode.STRING ->
                mPreferences.setString(property.name, value as String)

            ImplementationMode.FLOAT ->
                mPreferences.setFloat(property.name, value as Float)
        }
    }

    private fun findImplementation(): ImplementationMode {
        return when(mDefValue) {
            is Boolean -> ImplementationMode.BOOLEAN
            is Int -> ImplementationMode.INT
            is String -> ImplementationMode.STRING
            is Float -> ImplementationMode.FLOAT
            else -> throw Exception("Not found implementation for " + mDefValue!!::class.java.name)
        }
    }

    enum class ImplementationMode {
        BOOLEAN,
        STRING,
        FLOAT,
        INT
    }
}