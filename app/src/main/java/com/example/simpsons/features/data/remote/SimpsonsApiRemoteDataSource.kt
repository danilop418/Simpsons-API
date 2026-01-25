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
        return apiCall { apiService.findAll(1) }.map { response ->
            response.results
        }
    }

    suspend fun getSimpsonById(id: String): Result<SimpsonsApiModel> {
        return apiCall { apiService.findById(id) }
    }

    fun getPagingSource(): androidx.paging.PagingSource<Int, com.example.simpsons.features.domain.Simpson> {
        return com.example.simpsons.features.data.paging.SimpsonsPagingSource(apiService)
    }
}