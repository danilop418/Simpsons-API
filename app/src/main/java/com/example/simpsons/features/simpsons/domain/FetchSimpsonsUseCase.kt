package com.example.simpsons.features.simpsons.domain

class FetchSimpsonsUseCase(private val simpsonsRepository: SimpsonsRepository) {
    suspend operator fun invoke(): Result<List<Simpson>> {
        return simpsonsRepository.findAll()
    }
}