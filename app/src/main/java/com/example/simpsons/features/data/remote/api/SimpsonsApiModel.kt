package com.example.simpsons.features.data.remote.api

import com.google.gson.annotations.SerializedName

data class SimpsonsApiModel(
    val id: Int,
    val name: String,

    @SerializedName("portrait_path")
    val portraitPath: String,

    val phrases: List<String>
)