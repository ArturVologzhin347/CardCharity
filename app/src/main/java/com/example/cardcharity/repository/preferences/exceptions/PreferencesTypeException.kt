package com.example.cardcharity.repository.preferences.exceptions

class PreferencesTypeException(
    storeName: String,
    key: String,
    value: Any
) : PreferencesException(
    storeName,
    key,
    "Value: $value; Type: ${value::class.java.name}; This type is not implemented"
)
