package com.example.simpsons.features.simpsons.data

import com.example.simpsons.features.simpsons.data.remote.api.SimpsonsApiRemoteDataSource
import com.example.simpsons.features.simpsons.data.remote.api.toModel
import com.example.simpsons.features.simpsons.domain.Simpson
import com.example.simpsons.features.simpsons.domain.SimpsonsRepository

class SimpsonsDataRepository(
    private val apiRemoteDataSource: SimpsonsApiRemoteDataSource
) : SimpsonsRepository {

    override suspend fun fetch(): Result<List<Simpson>> {
        return apiRemoteDataSource.getSimpsons().map { apiModelsList ->
            apiModelsList.map { apiModel -> apiModel.toModel() }
        }
    }

    override suspend fun getSimpsonById(id: String): Result<Simpson> {
        return apiRemoteDataSource.getSimpsonById(id).map { apiModel ->
            apiModel.toModel()
        }
    }
}