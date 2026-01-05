package com.example.simpsons.features.domain.usecases

import com.example.simpsons.features.domain.Simpson
import com.example.simpsons.features.domain.SimpsonsRepository
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Single

@Single
class FetchSimpsonsUseCase(private val simpsonsRepository: SimpsonsRepository) {
    suspend operator fun invoke(): Result<List<Simpson>> {
        return simpsonsRepository.findAll()
    }
}