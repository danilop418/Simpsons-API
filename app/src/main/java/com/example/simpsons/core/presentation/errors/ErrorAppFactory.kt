package com.example.simpsons.core.presentation.errors

import android.content.Context
import com.example.simpsons.core.domain.ErrorApp

class ErrorAppFactory(private val context: Context) {

    fun build(errorApp: ErrorApp, onRetry: () -> Unit): ErrorAppUI {
        return when (errorApp) {
            is ErrorApp.InternetConexionError -> ConnectionErrorAppUI(context, onRetry)
            is ErrorApp.ServerErrorApp -> ServerErrorAppUI(context, onRetry)
            is ErrorApp.CacheError -> ServerErrorAppUI(context, onRetry)
        }
    }
}