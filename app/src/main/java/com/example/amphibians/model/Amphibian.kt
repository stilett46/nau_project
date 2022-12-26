package com.example.amphibians.model

import kotlinx.serialization.Serializable

@Serializable
data class Amphibian(
    val name: String,
    val type: String,
    val description: String,
    val img: String
)
