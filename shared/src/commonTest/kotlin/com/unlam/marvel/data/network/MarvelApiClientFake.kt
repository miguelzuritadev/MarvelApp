package com.unlam.marvel.data.network

class MarvelApiClientFake : IMarvelApiClient {
    // Create a fake CharactersResponse
    val fakeCharacters = listOf(
        CharacterResult(id = 1, name = "Spider-Man", description = "Friendly neighborhood Spider-Man", thumbnail = Thumbnail(path = "http://example.com/spiderman", extension = "jpg")),
        CharacterResult(id = 2, name = "Iron Man", description = "Genius, billionaire, playboy, philanthropist", thumbnail = Thumbnail(path = "http://example.com/ironman", extension = "jpg")),
    )

    val charactersResponse = CharactersResponse(data = CharacterData(results = fakeCharacters))

    override suspend fun getCharacters(timestamp: Long, md5: String): CharactersResponse = charactersResponse
}