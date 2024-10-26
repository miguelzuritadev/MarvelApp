package com.unlam.marvel.network

import kotlinx.serialization.Serializable

@Serializable
data class CharacterData(
    val results: List<CharacterResult>
)

