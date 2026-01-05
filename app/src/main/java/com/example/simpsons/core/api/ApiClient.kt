package com.example.simpsons.core.api

import org.koin.core.annotation.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Single
class ApiClient {
    private val BASE_URL = "https://thesimpsonsapi.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> createService(typeClass: Class<T>): T {
        return retrofit.create(typeClass)
    }
}