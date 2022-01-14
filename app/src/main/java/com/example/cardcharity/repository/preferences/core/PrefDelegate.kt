package com.example.cardcharity.repository.preferences.core

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class PrefDelegate<T : Any>(
    private val preferences: PreferencesStore,
    private val defaultValue: T
) : ReadWriteProperty<Any?, T> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return preferences.get(property.name, defaultValue)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        preferences.set(property.name, value)
    }
}
