package com.example.cardcharity.domain.common

sealed class Event<out T> {

    data class Success<out T>(val data: T) : Event<T>()

    data class Fail(val throwable: Throwable) : Event<Nothing>()

    object Load : Event<Nothing>()

    companion object {

        fun <T> success(data: T) = Success(data)

        fun fail(throwable: Throwable): Fail = Fail(throwable)

        fun load() = Load

    }
}