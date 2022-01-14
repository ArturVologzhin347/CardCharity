package com.example.cardcharity.repository.preferences.exceptions

class PreferencesEmptyException(
    storeName: String,
    key: String
) : PreferencesException(storeName, key, "Value is empty")
