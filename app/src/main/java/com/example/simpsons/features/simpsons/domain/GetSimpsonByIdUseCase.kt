package com.example.simpsons.features.simpsons.domain

class GetSimpsonByIdUseCase(private val simpsonsRepository: SimpsonsRepository) {
    suspend operator fun invoke(id: String): Result<Simpson> {
        return simpsonsRepository.findById(id)
    }
}