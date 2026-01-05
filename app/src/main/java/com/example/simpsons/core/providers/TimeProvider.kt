package com.example.simpsons.core.providers

import org.koin.core.annotation.Single

@Single
class TimeProvider {
    fun getCurrentTimeInMs(): Long = System.currentTimeMillis()
}
