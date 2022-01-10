package com.example.cardcharity.utils.extensions

import com.example.cardcharity.repository.model.User

val User?.isAuthorized: Boolean
    get() = this != null

val User.abbreviation: String
    get() = if (!name.isNullOrEmpty())
        name.toAbbreviation(maxLength = 2) else email.firstInUppercase()
