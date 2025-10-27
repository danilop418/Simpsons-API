package com.example.simpsons.features.simpsons.data.remote.api

import com.google.gson.annotations.SerializedName

data class SimpsonsApiResponse(
    val count: Int,
    val next: String?,
    val prev: String?,
    val pages: Int,
    val results: List<SimpsonsApiModel>
)
data class SimpsonsApiModel(
    val id: Int,
    val name: String,

    @SerializedName("portrait_path")
    val portraitPath: String,

    val phrases: List<String>
)

