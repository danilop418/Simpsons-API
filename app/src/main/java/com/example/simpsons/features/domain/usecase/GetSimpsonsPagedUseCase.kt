package com.example.simpsons.features.domain.usecase

import androidx.paging.PagingData
import com.example.simpsons.features.domain.Simpson
import com.example.simpsons.features.domain.SimpsonsRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class GetSimpsonsPagedUseCase(
    private val repository: SimpsonsRepository
) {
    operator fun invoke(): Flow<PagingData<Simpson>> {
        return repository.getSimpsonsPager()
    }
}
