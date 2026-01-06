package com.example.simpsons.features.data.remote

import com.example.simpsons.features.domain.Simpson

fun SimpsonsApiModel.toModel(): Simpson {
    val fullImageUrl = "https://cdn.thesimpsonsapi.com/500${this.portraitPath}"
    return Simpson(
        id = this.id.toString(),
        name = this.name,
        phrase = this.phrases.firstOrNull() ?: "",
        imageUrl = fullImageUrl
    )
}