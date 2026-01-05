package com.example.simpsons.core.errors

sealed class ErrorApp: Throwable() {

    object InternetConexionError : ErrorApp()

    object ServerErrorApp : ErrorApp()

}