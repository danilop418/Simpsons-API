package com.example.simpsons.features.data.local.mem

import com.example.simpsons.features.domain.Simpson
import org.koin.core.annotation.Single

@Single
class SimpsonsMemLocalDataSource {

    private val simpsons: ArrayList<Simpson> = arrayListOf()

    fun obtain(): Result<List<Simpson>> {
        return if (simpsons.isNotEmpty()) {
            Result.success(simpsons.toList())
        } else {
            Result.success(emptyList())
        }
    }

    fun save(simpsons: List<Simpson>) {
        this.simpsons.clear()
        this.simpsons.addAll(simpsons)
    }

    fun isEmpty(): Boolean = simpsons.isEmpty()

    fun clear() {
        simpsons.clear()
    }
}
