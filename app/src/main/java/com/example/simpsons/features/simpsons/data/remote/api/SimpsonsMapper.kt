package com.example.simpsons.features.simpsons.data.remote.api

import com.example.simpsons.features.simpsons.domain.Simpson

fun SimpsonsApiModel.toModel(): Simpson {
    return Simpson(
        id = this.id.toString(),
        name = this.name,
        phrase = this.phrases.firstOrNull() ?: "",
        imageUrl = "https://thesimpsonsapi.com${this.portraitPath}"
    )
}