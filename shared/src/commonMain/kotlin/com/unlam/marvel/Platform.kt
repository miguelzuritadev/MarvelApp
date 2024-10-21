package com.unlam.marvel

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform