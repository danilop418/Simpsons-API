package com.example.simpsons.features.data.remote

import com.example.simpsons.core.data.remote.api.ApiClient
import com.example.simpsons.core.data.remote.api.apiCall
import com.example.simpsons.features.data.remote.SimpsonsApiService
import org.koin.core.annotation.Single

@Single
class SimpsonsApiRemoteDataSource(private val apiClient: ApiClient) {

    private val apiService: SimpsonsApiService by lazy {
        apiClient.createService(SimpsonsApiService::class.java)
    }

    suspend fun getSimpsons(): Result<List<SimpsonsApiModel>> {
        return apiCall { apiService.findAll() }.map { response ->
            response.results
        }
    }

    suspend fun getSimpsonById(id: String): Result<SimpsonsApiModel> {
        return apiCall { apiService.findById(id) }
    }
}