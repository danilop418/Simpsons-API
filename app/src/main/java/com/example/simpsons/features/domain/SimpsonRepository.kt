package com.example.simpsons.features.domain

interface SimpsonsRepository {
    suspend fun findAll(): Result<List<Simpson>>
    suspend fun findById(id: String): Result<Simpson>
    fun getSimpsonsPager(): kotlinx.coroutines.flow.Flow<androidx.paging.PagingData<Simpson>>
}