package com.example.simpsons.features.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimpsonsApiResponse(
    @SerialName("results") val results: List<SimpsonsApiModel>,
    @SerialName("next") val next: String? = null,
    @SerialName("prev") val prev: String? = null,
    @SerialName("count") val count: Int? = null,
    @SerialName("pages") val pages: Int? = null
)