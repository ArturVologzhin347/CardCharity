package com.example.cardcharity.repository.preferences.exceptions

abstract class PreferencesException(
    storeName: String,
    key: String,
    message: String = "YOU FORGOT TO WRITE A MESSAGE"
) : Exception("Store: $storeName; Key: $key; $message")
