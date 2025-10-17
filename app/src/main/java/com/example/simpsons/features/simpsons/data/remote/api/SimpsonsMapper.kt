package com.example.simpsons.features.simpsons.data.remote.api

import com.example.simpsons.features.simpsons.domain.Simpson

fun SimpsonsApiModel.toModel(): Simpson {
    return Simpson(
        this.id,
        this.name,
        this.phrase,
        this.imageUrl
    )
}