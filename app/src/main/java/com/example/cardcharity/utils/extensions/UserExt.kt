package com.example.cardcharity.utils.extensions

import com.example.cardcharity.repository.model.User

fun User?.isAuthorized(): Boolean {
    return this != null
}
