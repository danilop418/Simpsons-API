package com.example.simpsons.features.simpsons.domain

interface SimpsonsRepository {
    suspend fun findAll(): Result<List<Simpson>>
    suspend fun findById(id: String): Result<Simpson>
}