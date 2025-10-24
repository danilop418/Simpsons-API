package com.example.simpsons.features.simpsons.domain

class GetAllCharactersUseCase(private val repository: SimpsonsRepository) {
    suspend operator fun invoke(): Result<List<Simpson>> {
        return repository.findAll()
    }
}