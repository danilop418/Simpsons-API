package com.example.simpsons.features.simpsons.domain

class FetchSimpsonsUseCase(private val simpsonsRepository: SimpsonsRepository) {
    suspend fun fetch(): Result<List<Simpson>> {
        return simpsonsRepository.fetch()
    }
}