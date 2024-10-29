package com.unlam.marvel.data.network

import kotlinx.serialization.Serializable


@Serializable
data class Thumbnail(
    val path: String,
    val extension: String
) {
    fun toUrl(): String {
        return "$path.$extension"
    }
}