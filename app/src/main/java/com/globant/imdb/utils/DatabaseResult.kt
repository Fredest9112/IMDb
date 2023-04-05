package com.globant.imdb.utils

sealed class DatabaseResult {
    data class DatabaseSuccess (val message: String, val successful: Boolean = true): DatabaseResult()
    data class DatabaseError (val message: String, val successful: Boolean = false): DatabaseResult()
}