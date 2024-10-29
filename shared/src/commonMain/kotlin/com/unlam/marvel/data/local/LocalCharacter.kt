package com.unlam.marvel.data.local

import com.unlam.marvel.domain.model.Character
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocalCharacter(
    @SerialName(value = "id")
    val id: Long,
    @SerialName(value = "name")
    val name: String,
    @SerialName(value = "description")
    val description: String?,
    @SerialName(value = "thumbnail_url")
    val url: String?
) {
    fun toModel(): Character {
        return Character(
            id = id,
            name = name,
            description = description.toString(),
            thumbnailUrl = url.toString()
        )
    }
}
