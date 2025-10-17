package com.example.simpsons.features.simpsons.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SimpsonsApiService {
    @GET("api/characters")
    suspend fun findAll(): Response<List<SimpsonsApiModel>>

    @GET("api/characters/{id}")
    suspend fun findById(@Path("id") id: String): Response<SimpsonsApiModel>
}