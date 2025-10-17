package com.example.simpsons.features.simpsons.data.remote.api

import com.example.simpsons.features.simpsons.data.core.api.ApiClient
import com.example.simpsons.features.simpsons.domain.ErrorApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SimpsonsApiRemoteDataSource(private val apiClient: ApiClient) {

    suspend fun getSimpsons(): Result<List<SimpsonsApiModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val apiService = apiClient.createService(SimpsonsApiService::class.java)
                val response = apiService.findAll()

                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(ErrorApp.ServerErrorApp)
                }
            } catch (e: Exception) {
                Result.failure(ErrorApp.InternetConexionError)
            }
        }
    }

    suspend fun getSimpsonById(id: String): Result<SimpsonsApiModel> {
        return withContext(Dispatchers.IO) {
            try {
                val apiService = apiClient.createService(SimpsonsApiService::class.java)
                val response = apiService.findById(id)

                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(ErrorApp.ServerErrorApp)
                }
            } catch (e: Exception) {
                Result.failure(ErrorApp.InternetConexionError)
            }
        }
    }
}