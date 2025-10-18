package com.example.simpsons.features.simpsons.data.remote.api

import android.util.Log
import com.example.simpsons.features.simpsons.domain.Simpson

fun SimpsonsApiModel.toModel(): Simpson {
    val fullImageUrl = "https://cdn.thesimpsonsapi.com/500${this.portraitPath}"
    Log.d("MAPPER_DEBUG", "Portrait path: ${this.portraitPath}")
    Log.d("MAPPER_DEBUG", "URL construida: $fullImageUrl")

    return Simpson(
        id = this.id.toString(),
        name = this.name,
        phrase = this.phrases.firstOrNull() ?: "",
        imageUrl = fullImageUrl
    )
}