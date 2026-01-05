package com.example.simpsons.core.api

import android.util.Log
import com.example.simpsons.core.errors.ErrorApp
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
            Log.d("API_CALL", "Response code: ${response.code()}")

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Log.e("API_CALL", "Server error: ${response.code()}")
                Result.failure(ErrorApp.ServerErrorApp)
            }
        }.getOrElse { exception ->
            Log.e("API_CALL", "Exception: ${exception.message}")
            when (exception) {
                is IOException -> Result.failure(ErrorApp.InternetConexionError)
                else -> Result.failure(ErrorApp.ServerErrorApp)
            }
        }
    }
}
