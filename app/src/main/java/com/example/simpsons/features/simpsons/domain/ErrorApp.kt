package com.example.simpsons.features.simpsons.domain

sealed class ErrorApp : Throwable() {

    object InternetConexionError : ErrorApp()

    object ServerErrorApp : ErrorApp()

}