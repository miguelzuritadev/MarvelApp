package com.unlam.marvel.ui

import androidx.compose.runtime.mutableStateOf
import com.unlam.marvel.data.local.LocalCharacter
import com.unlam.marvel.data.local.LocalCharacterRepository
import com.unlam.marvel.domain.model.Character
import org.koin.core.component.KoinComponent

class AppViewModel(val localRepository: LocalCharacterRepository) : KoinComponent {

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
}