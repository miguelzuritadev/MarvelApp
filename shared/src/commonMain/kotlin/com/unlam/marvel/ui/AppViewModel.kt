package com.unlam.marvel.ui

import androidx.compose.runtime.mutableStateOf
import com.unlam.marvel.Crypto.md5
import com.unlam.marvel.PRIVATE_KEY
import com.unlam.marvel.PUBLIC_KEY
import com.unlam.marvel.data.local.LocalCharacter
import com.unlam.marvel.data.local.ILocalCharacterRepository
import com.unlam.marvel.data.network.IMarvelRepository
import com.unlam.marvel.domain.model.Character
import org.koin.core.component.KoinComponent

class AppViewModel(val localRepository: ILocalCharacterRepository, val networkRepository: IMarvelRepository) : KoinComponent {

    val items = mutableStateOf(listOf<Character>())

    fun sayHello(): String {
        return "Hello, Marvel!"
    }

    suspend fun getLocalCharacters() {
        val characterList: List<LocalCharacter> = localRepository.getAll()
        items.value = characterList.map {
            it.toModel()
        }

        println("==characterList: $characterList==")
    }

    /**
     * Save characters in local cache
     */
    suspend fun saveLocalCharacters(characters: List<Character>) {
        localRepository.deleteAll()
        characters.forEach {
            val localCharacter = LocalCharacter(
                it.id,
                it.name,
                it.description,
                it.thumbnailUrl
            )
            localRepository.insert(localCharacter)
        }
    }

    suspend fun getNetworkCharactersAndSaveLocalCache(timestamp: Long) {
        val characters = networkRepository.getCharacters(timestamp, md5(timestamp.toString() + PRIVATE_KEY + PUBLIC_KEY))
        items.value = characters
        saveLocalCharacters(characters)
    }
}