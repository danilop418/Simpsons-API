package com.example.simpsons.features.simpsons.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SimpsonsApiService {
    @GET("api/characters")
    suspend fun findAll(
        @Query("page") page: Int = 1
    ): Response<SimpsonsApiResponse>

    @GET("api/characters/{id}")
    suspend fun findById(@Path("id") id: String): Response<SimpsonsApiModel>
}