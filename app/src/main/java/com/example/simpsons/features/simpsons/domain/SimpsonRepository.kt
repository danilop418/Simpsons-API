package com.example.simpsons.features.simpsons.domain

interface SimpsonsRepository {
    suspend fun fetch(): Result<List<Simpson>>
    suspend fun getSimpsonById(id: String): Result<Simpson>
}