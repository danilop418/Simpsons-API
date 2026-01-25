package com.example.simpsons.features.data

import com.example.simpsons.features.data.local.mem.SimpsonsMemLocalDataSource
import com.example.simpsons.features.data.local.xml.SimpsonsXmlLocalDataSource
import com.example.simpsons.features.data.remote.SimpsonsApiRemoteDataSource
import com.example.simpsons.features.data.remote.toModel
import com.example.simpsons.features.domain.Simpson
import com.example.simpsons.features.domain.SimpsonsRepository
import org.koin.core.annotation.Single

@Single(binds = [SimpsonsRepository::class])
class SimpsonsDataRepository(
    private val memLocalDataSource: SimpsonsMemLocalDataSource,
    private val xmlLocalDataSource: SimpsonsXmlLocalDataSource,
    private val apiRemoteDataSource: SimpsonsApiRemoteDataSource
) : SimpsonsRepository {

    override suspend fun findAll(): Result<List<Simpson>> {
        val memResult = memLocalDataSource.obtain()
        if (memResult.isSuccess && memResult.getOrNull()?.isNotEmpty() == true) {
            return memResult
        }

        val xmlResult = xmlLocalDataSource.findAll()
        if (xmlResult.isSuccess && xmlResult.getOrNull()?.isNotEmpty() == true) {
            memLocalDataSource.save(xmlResult.getOrNull()!!)
            return xmlResult
        }

        return apiRemoteDataSource.getSimpsons().map { apiModelsList ->
            val simpsons = apiModelsList.map { apiModel -> apiModel.toModel() }
            memLocalDataSource.save(simpsons)
            xmlLocalDataSource.save(simpsons)
            simpsons
        }
    }

    override suspend fun findById(id: String): Result<Simpson> {
        return apiRemoteDataSource.getSimpsonById(id).map { apiModel ->
            apiModel.toModel()
        }
    }

    override fun getSimpsonsPager(): kotlinx.coroutines.flow.Flow<androidx.paging.PagingData<Simpson>> {
        return androidx.paging.Pager(
            config = androidx.paging.PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { apiRemoteDataSource.getPagingSource() }
        ).flow
    }
}