package com.example.simpsons.features.data.local.xml

import com.example.simpsons.core.data.local.xml.XmlCacheStorage
import com.example.simpsons.core.data.local.xml.policies.CachePolicy
import com.example.simpsons.core.errors.ErrorApp
import com.example.simpsons.core.providers.TimeProvider
import com.example.simpsons.features.domain.Simpson
import org.koin.core.annotation.Single

@Single
class SimpsonsXmlLocalDataSource(
    private val xmlCacheStorage: XmlCacheStorage<SimpsonXmlModel>,
    private val cachePolicy: CachePolicy<SimpsonXmlModel>,
    private val timeProvider: TimeProvider
) {

    fun findAll(): Result<List<Simpson>> {
        return xmlCacheStorage.obtainAll().fold(
            onSuccess = { simpsonXmlModels ->
                val simpsons: MutableList<Simpson> = mutableListOf()
                simpsonXmlModels.forEach { xmlModel ->
                    if (cachePolicy.isValid(xmlModel)) {
                        simpsons.add(xmlModel.toModel())
                    }
                }
                Result.success(simpsons)
            },
            onFailure = {
                Result.failure(ErrorApp.CacheError)
            }
        )
    }

    fun find(simpsonId: String): Result<Simpson?> {
        return xmlCacheStorage.obtain(simpsonId).fold(
            onSuccess = { simpsonXmlModel ->
                if (simpsonXmlModel != null && cachePolicy.isValid(simpsonXmlModel)) {
                    Result.success(simpsonXmlModel.simpson)
                } else {
                    Result.success(null)
                }
            },
            onFailure = {
                Result.failure(ErrorApp.CacheError)
            }
        )
    }

    fun save(simpsons: List<Simpson>): Result<Boolean> {
        val createdAt: Long = timeProvider.getCurrentTimeInMs()
        return xmlCacheStorage.save(simpsons.map { it.toXmlModel(createdAt) })
    }

    fun save(simpson: Simpson): Result<Boolean> {
        val createdAt: Long = timeProvider.getCurrentTimeInMs()
        return xmlCacheStorage.save(simpson.toXmlModel(createdAt))
    }

    fun clear() {
        xmlCacheStorage.clear()
    }
}
