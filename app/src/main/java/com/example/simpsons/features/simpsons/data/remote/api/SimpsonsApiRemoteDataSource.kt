package com.example.simpsons.features.simpsons.data.remote.api

import android.util.Log
import com.example.simpsons.features.simpsons.core.api.ApiClient
import com.example.simpsons.features.simpsons.domain.ErrorApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SimpsonsApiRemoteDataSource(private val apiClient: ApiClient) {

    suspend fun getSimpsons(): Result<List<SimpsonsApiModel>> {
        return withContext(Dispatchers.IO) {
            Log.d("API_DEBUG", "=== INICIO PETICIÓN ===")
            Log.d("API_DEBUG", "Thread: ${Thread.currentThread().name}")

            val apiService = apiClient.createService(SimpsonsApiService::class.java)
            Log.d("API_DEBUG", "ApiService creado")

            val response = apiService.findAll()
            Log.d("API_DEBUG", "Response code: ${response.code()}")
            Log.d("API_DEBUG", "Response message: ${response.message()}")

            if (response.isSuccessful) {
                val characters = response.body()!!.results

                Log.d("API_DEBUG", "ÉXITO: ${characters.size} personajes")
                Result.success(characters)
            } else {
                Log.e("API_DEBUG", "Error servidor: ${response.code()}")
                Result.failure(ErrorApp.ServerErrorApp)
            }
        }
    }

    suspend fun getSimpsonById(id: String): Result<SimpsonsApiModel> {
        return withContext(Dispatchers.IO) {
            Log.d("API_DEBUG", "Buscando Simpson ID: $id")
            val apiService = apiClient.createService(SimpsonsApiService::class.java)
            val response = apiService.findById(id)

            if (response.isSuccessful && response.body() != null) {
                Log.d("API_DEBUG", "Simpson encontrado: ${response.body()!!.name}")
                Result.success(response.body()!!)
            } else {
                Log.e("API_DEBUG", "Error: ${response.code()}")
                Result.failure(ErrorApp.ServerErrorApp)
            }
        }
    }
}