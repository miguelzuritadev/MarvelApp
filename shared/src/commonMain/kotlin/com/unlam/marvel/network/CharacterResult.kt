package com.unlam.marvel.network

import kotlinx.serialization.Serializable

@Serializable
data class CharacterResult(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
)
