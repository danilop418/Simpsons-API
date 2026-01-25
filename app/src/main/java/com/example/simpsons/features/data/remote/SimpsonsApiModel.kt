package com.example.simpsons.features.data.remote

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimpsonsApiModel(
    val id: Int,
    val name: String,

    @SerializedName("portrait_path")
    @SerialName("portrait_path")
    val portraitPath: String,

    val phrases: List<String>,

    val description: String?,
    val gender: String?,
    val occupation: String?,
    val status: String?
)