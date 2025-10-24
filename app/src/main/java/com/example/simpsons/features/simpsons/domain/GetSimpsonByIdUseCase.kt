package com.example.simpsons.features.simpsons.domain

class GetSimpsonByIdUseCase(private val simpsonsRepository: SimpsonsRepository) {
    suspend fun getSimpsonById(id: String): Result<Simpson> {
        return simpsonsRepository.findById(id)
    }
}