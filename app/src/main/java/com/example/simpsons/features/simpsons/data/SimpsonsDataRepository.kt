package com.example.simpsons.features.simpsons.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.simpsons.features.simpsons.data.remote.api.SimpsonsApiRemoteDataSource
import com.example.simpsons.features.simpsons.data.remote.api.toModel
import com.example.simpsons.features.simpsons.domain.Simpson
import com.example.simpsons.features.simpsons.domain.SimpsonsRepository
import kotlinx.coroutines.flow.Flow

class SimpsonsDataRepository(
    private val apiRemoteDataSource: SimpsonsApiRemoteDataSource
) : SimpsonsRepository {

    override suspend fun findAll(): Result<List<Simpson>> {
        return apiRemoteDataSource.getSimpsons().map { apiModelsList ->
            apiModelsList.map { apiModel -> apiModel.toModel() }
        }
    }

    override suspend fun findById(id: String): Result<Simpson> {
        return apiRemoteDataSource.getSimpsonById(id).map { apiModel ->
            apiModel.toModel()
        }
    }

    override suspend fun getSearchResultStream(query: String): Flow<PagingData<Simpson>> {
        return Pager(
            config = PagingConfig(pageSize = 50),
            pagingSourceFactory = {
                SimpsonsPagingSource(
                    service = apiRemoteDataSource.getSimpsons()
                            query = query
                )
            }
        ).flow
    }
}