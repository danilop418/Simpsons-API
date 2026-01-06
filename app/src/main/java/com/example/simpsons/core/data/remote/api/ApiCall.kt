package com.example.simpsons.core.data.remote.api

import com.example.simpsons.core.domain.ErrorApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

suspend fun <T> apiCall(
    call: suspend () -> Response<T>
): Result<T> {
    return withContext(Dispatchers.IO) {
        runCatching {
            val response = call()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(ErrorApp.ServerErrorApp)
            }
        }.getOrElse { exception ->
            when (exception) {
                is IOException -> Result.failure(ErrorApp.InternetConexionError)
                else -> Result.failure(ErrorApp.ServerErrorApp)
            }
        }
    }
}
