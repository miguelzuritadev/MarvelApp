package com.unlam.marvel.data.network

import com.unlam.marvel.domain.model.Character
import kotlinx.serialization.Serializable


@Serializable
data class CharactersResponse(
    val data: CharacterData? = null
) {
    fun toModel(): List<Character> {
        return this.data?.results?.map {
            Character(
                id = it.id,
                name = it.name,
                description = it.description,
                thumbnailUrl = it.thumbnail.toUrl()
            )
        } ?: emptyList()
    }
}

