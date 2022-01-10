package com.example.cardcharity.domain.common

interface DataAdapter<I, O> {
    suspend fun format(input: I): O
}
