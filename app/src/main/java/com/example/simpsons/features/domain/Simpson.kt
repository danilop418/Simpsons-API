package com.example.simpsons.features.domain

import kotlinx.serialization.Serializable

@Serializable
data class Simpson(
    val id: String,
    val name: String,
    val phrase: String,
    val imageUrl: String
)