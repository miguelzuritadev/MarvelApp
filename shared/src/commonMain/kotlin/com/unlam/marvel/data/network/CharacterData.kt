package com.unlam.marvel.data.network

import kotlinx.serialization.Serializable

@Serializable
data class CharacterData(
    val results: List<CharacterResult>
)

