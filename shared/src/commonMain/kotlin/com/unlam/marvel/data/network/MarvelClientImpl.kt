package com.unlam.marvel.data.network

import com.unlam.marvel.PUBLIC_KEY
import com.unlam.marvel.initLogger
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class MarvelClientImpl: IMarvelClient {
    val baseUrl = "gateway.marvel.com"

    private val client = HttpClient() {
        install(Logging){
            level = LogLevel.ALL
            logger = object:Logger{
                override fun log(message: String) {
                    Napier.v(tag = "KtorHttpClient", message = message)
                }
            }
        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
        defaultRequest {
            host = baseUrl
            url {
                protocol = URLProtocol.HTTPS
                parameters.append("apikey", PUBLIC_KEY)
            }
            contentType(ContentType.Application.Json)

        }
    }.also {
        initLogger()
    }

    override suspend fun getCharacters(timestamp: Long, md5: String): CharactersResponse {
        val response = client.get("v1/public/characters") {
            parameter("ts", timestamp)
            parameter("hash", md5)
            parameter("limit", 10)
        }
        return response.body()
    }
}