package com.example.simpsons.features.domain.usecases

import com.example.simpsons.features.domain.Simpson
import com.example.simpsons.features.domain.SimpsonsRepository
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Single

@Single
class GetSimpsonByIdUseCase(private val simpsonsRepository: SimpsonsRepository) {
    suspend operator fun invoke(id: String): Result<Simpson> {
        return simpsonsRepository.findById(id)
    }
}