package com.unlam.marvel.ui

import androidx.compose.runtime.mutableStateOf
import com.unlam.marvel.domain.model.Character

class AppViewModel {

    val items = mutableStateOf(listOf<Character>())

    fun sayHello(): String {
        return "Hello, Marvel!"
    }
}