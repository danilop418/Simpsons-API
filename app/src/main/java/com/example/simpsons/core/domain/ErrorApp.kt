package com.example.simpsons.core.domain

sealed class ErrorApp: Throwable() {

    object InternetConexionError : ErrorApp()

    object ServerErrorApp : ErrorApp()

    object CacheError : ErrorApp()
}