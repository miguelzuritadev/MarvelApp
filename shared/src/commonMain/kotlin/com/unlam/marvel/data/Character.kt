package com.unlam.marvel.data

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
)
