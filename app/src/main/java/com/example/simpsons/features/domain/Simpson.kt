package com.example.simpsons.features.domain

import kotlinx.serialization.Serializable

@Serializable
data class Simpson(
    val id: String,
    val name: String,
    val phrase: String,
    val imageUrl: String,
    val description: String = "",
    val gender: String = "",
    val occupation: String = "",
    val status: String = ""
)